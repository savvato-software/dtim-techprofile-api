package org.haxwell.dtim.techprofile.services;

import java.math.BigInteger;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.haxwell.dtim.techprofile.entities.CandidateTechProfileLineItemScore;
import org.haxwell.dtim.techprofile.entities.TechProfile;
import org.haxwell.dtim.techprofile.entities.TechProfileLineItem;
import org.haxwell.dtim.techprofile.entities.TechProfileTopic;
import org.haxwell.dtim.techprofile.repositories.CandidateTechProfileLineItemScoreRepository;
import org.haxwell.dtim.techprofile.repositories.TechProfileLineItemRepository;
import org.haxwell.dtim.techprofile.repositories.TechProfileRepository;
import org.haxwell.dtim.techprofile.repositories.TechProfileTopicRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TechProfileServiceImpl implements TechProfileService {

	@PersistenceContext
	EntityManager em;
	
	@Autowired
	TechProfileRepository techProfileRepository;
	
	@Autowired
	TechProfileTopicRepository techProfileTopicRepository;
	
	@Autowired
	TechProfileLineItemRepository techProfileLineItemRepository;
	
	@Autowired
	CandidateTechProfileLineItemScoreRepository ctplisRepository;
	
	@Override
	public TechProfile get(Long id) {
		Optional<TechProfile> opt = techProfileRepository.findById(id);
		
		List topicSequences = getTopicSequences(id);
		List lineItemSequences = getLineItemSequences(id);
		
		TechProfile tp = null;
		
		if (opt.isPresent()) {
			tp = opt.get();
			
			Set<TechProfileTopic> topics = tp.getTopics();
			
			Iterator<TechProfileTopic> tptIterator = topics.iterator();
			
			while (tptIterator.hasNext()) {
				TechProfileTopic topic = tptIterator.next();
				
				setSequenceOnTopic(topic, topicSequences);
				
				Set<TechProfileLineItem> lineItems = topic.getLineItems();
				
				Iterator<TechProfileLineItem> tpliIterator = lineItems.iterator();
				
				while (tpliIterator.hasNext()) {
					TechProfileLineItem tpli = tpliIterator.next();
					
					setSequenceOnLineItem(topic.getId(), tpli, lineItemSequences);
				}
			}
		}
		
		return tp;
	}
	
	@Override
	@Transactional
	public TechProfileTopic addTopic(String topicName) {
		TechProfileTopic rtn = techProfileTopicRepository.save(new TechProfileTopic(topicName));
		
		Long currentMaxSequenceNum = Long.parseLong(em.createNativeQuery("SELECT max(sequence) FROM tech_profile_topic_map where tech_profile_id=1")
				.getResultList().get(0).toString());
		
		em.createNativeQuery("INSERT INTO tech_profile_topic_map (tech_profile_id, tech_profile_topic_id, sequence) VALUES (1, :topicId, :sequence)")
			.setParameter("topicId",  rtn.getId())
			.setParameter("sequence", currentMaxSequenceNum + 1)
			.executeUpdate();
		
		return rtn;
	}
	
	@Override
	@Transactional
	public TechProfileLineItem addLineItem(Long topicId, String lineItemName, String l0desc, String l1desc, String l2desc, String l3desc) {
		TechProfileLineItem rtn = techProfileLineItemRepository.save(new TechProfileLineItem(lineItemName, l0desc, l1desc, l2desc, l3desc));

		Long currentMaxSequenceNum = Long.parseLong(em.createNativeQuery("SELECT max(sequence) FROM tech_profile_topic_line_item_map where tech_profile_topic_id=:topicId")
				.setParameter("topicId", topicId)
				.getResultList().get(0).toString());
		
		if (topicId > 0) {
			em.createNativeQuery("INSERT INTO tech_profile_topic_line_item_map (tech_profile_topic_id, tech_profile_line_item_id, sequence) VALUES (:topicId, :lineItemId, :sequence)")
				.setParameter("topicId", topicId)
				.setParameter("lineItemId", rtn.getId())
				.setParameter("sequence", currentMaxSequenceNum + 1)
				.executeUpdate();
		}

		return rtn;
	}
	
	@Override
	public TechProfileLineItem updateLineItem(Long lineItemId, String lineItemName, String l0desc, String l1desc, String l2desc, String l3desc) {
		Optional<TechProfileLineItem> opt = techProfileLineItemRepository.findById(lineItemId);
		TechProfileLineItem rtn = null;
		
		if (opt.isPresent()) {
			TechProfileLineItem tpli = opt.get();
			
			tpli.setL0Description(l0desc);
			tpli.setL1Description(l1desc);
			tpli.setL2Description(l2desc);
			tpli.setL3Description(l3desc);
			
			tpli.setName(lineItemName);
			
			rtn = techProfileLineItemRepository.save(tpli);
		}

		return rtn;
	}
	
	@Override
	public List<CandidateTechProfileLineItemScore> getCandidateIdScores(Long candidateId) {
		return ctplisRepository.findByCandidateId(candidateId);
	}
	
	/**
	 * Returns the sequence of the topics in a given tech profile.
	 * 
	 * @param techProfileId
	 * @return
	 */
	private List getTopicSequences(Long techProfileId) {
		List resultList = em.createNativeQuery("SELECT tpt.id as topic_id, tptm.sequence FROM tech_profile tp, tech_profile_topic tpt, tech_profile_topic_map tptm WHERE tp.id=:techProfileId AND tptm.tech_profile_id=tp.id AND tptm.tech_profile_topic_id=tpt.id;")
				.setParameter("techProfileId", techProfileId).getResultList();
		
		return resultList;
	}
	
	private List getLineItemSequences(Long techProfileId) {
		List resultList = em.createNativeQuery("select tpt.id as topic_id, tpli.id as tech_profile_line_item_id, tptlim.sequence FROM tech_profile tp, tech_profile_topic tpt, tech_profile_topic_map tptm, tech_profile_line_item tpli, tech_profile_topic_line_item_map tptlim WHERE tp.id=:techProfileId and tptm.tech_profile_id=tp.id and tptm.tech_profile_topic_id=tpt.id and tpt.id=tptlim.tech_profile_topic_id and tptlim.tech_profile_line_item_id=tpli.id;")
				.setParameter("techProfileId", techProfileId).getResultList();
		
		return resultList;
	}

	private TechProfileTopic setSequenceOnTopic(TechProfileTopic topic, List topicSequences) {
		int x = 0;
		TechProfileTopic rtn = null;
		
		while (rtn == null && x < topicSequences.size()) {
			Object[] ts = (Object[])topicSequences.get(x++);
			
			if (((BigInteger)ts[0]).longValue() == (Long)topic.getId()) {
				topic.setSequence(((BigInteger)ts[1]).longValue());
				rtn = topic;
			}
		}
		
		return rtn;
	}
	
	private TechProfileLineItem setSequenceOnLineItem(Long topicId, TechProfileLineItem tpli, List lineItemSequences) {
		int x = 0;
		TechProfileLineItem rtn = null;

		while (rtn == null && x < lineItemSequences.size()) {
			Object[] lis = (Object[])lineItemSequences.get(x++);

			if (((BigInteger)lis[0]).longValue() == topicId && ((BigInteger)lis[1]).longValue() == (Long)tpli.getId()) {
				tpli.setSequence(((BigInteger)lis[2]).longValue());
				rtn = tpli;
			}
		}

		return rtn;
	}
}

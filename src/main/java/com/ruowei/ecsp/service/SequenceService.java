package com.ruowei.ecsp.service;

import com.ruowei.ecsp.repository.EcoResourceRepository;
import com.ruowei.ecsp.repository.HeadlineNewsRepository;
import com.ruowei.ecsp.util.StreamUtil;
import com.ruowei.ecsp.web.rest.imp.SequenceIMP;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;

@Service
@Slf4j
@Transactional
public class SequenceService {

    private final HeadlineNewsRepository headlineNewsRepository;
    private final EcoResourceRepository ecoResourceRepository;

    public SequenceService(EcoResourceRepository ecoResourceRepository, HeadlineNewsRepository headlineNewsRepository) {
        this.ecoResourceRepository = ecoResourceRepository;
        this.headlineNewsRepository = headlineNewsRepository;
    }

    public Integer getNewSequenceByType(String type) {
        Integer sequence;
        switch (type) {
            default:
                sequence = null;
                break;
            case "eco_resource":
                sequence = ecoResourceRepository.findMaxSequence();
                break;
            case "headline_news":
                sequence = headlineNewsRepository.findMaxSequence();
                break;
        }
        return sequence == null || sequence == 0 ? 1 : sequence + 1;
    }

    public void reorderSequence(List<Long> ids, List<? extends SequenceIMP> imps) {
        List<Integer> sequences = StreamUtil.sortedCollectV(imps, Comparator.comparing(SequenceIMP::getSequence), SequenceIMP::getSequence);
        for (int i = 0; i < ids.size(); i++) {
            Long id = ids.get(i);
            Integer sequence = sequences.get(i);
            imps.stream().filter(s -> s.getId().equals(id)).findFirst().ifPresent(s -> s.setSequence(sequence));
        }
    }

    public void reorderSequence(List<Long> ids, SequenceIMP... impArr) {
        List<Integer> sequences = StreamUtil.sortedCollectV(impArr, Comparator.comparing(SequenceIMP::getSequence), SequenceIMP::getSequence);
        for (int i = 0; i < ids.size(); i++) {
            Long id = ids.get(i);
            Integer sequence = sequences.get(i);
            Stream.of(impArr).filter(s -> s.getId().equals(id)).findFirst().ifPresent(s -> s.setSequence(sequence));
        }
    }

}

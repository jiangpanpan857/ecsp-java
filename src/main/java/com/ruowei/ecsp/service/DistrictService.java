//package com.ruowei.ecsp.service;
//
//import com.ruowei.ecsp.domain.District;
//import com.ruowei.ecsp.repository.DistrictRepository;
//import com.ruowei.ecsp.web.rest.dto.DistrictDTO;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.stereotype.Service;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Optional;
//import java.util.stream.Collectors;
//
//@Service
//@Slf4j
//public class DistrictService {
//
//    private final DistrictRepository districtRepository;
//
//    public DistrictService(DistrictRepository districtRepository) {
//        this.districtRepository = districtRepository;
//    }
//
//    public List<DistrictDTO> getDistrictTree() {
//        List<District> districtList = districtRepository.findAll();
//        List<DistrictDTO> tree = new ArrayList<>();
//        List<DistrictDTO> treeNodeList = new ArrayList<>();
//        for (District district : districtList) {
//            DistrictDTO districtDTO = new DistrictDTO();
//            districtDTO.setValue(String.valueOf(district.getId()));
//            districtDTO.setLabel(district.getExtName());
//            districtDTO.setChildren(new ArrayList<>());
//            //先在cacheTreeNodeList中找它的父节点
//            if (district.getPid() != null && district.getPid() != 0) {
//                Optional<DistrictDTO> parentOptional = treeNodeList
//                    .stream()
//                    .filter(node -> node.getValue().equals(String.valueOf(district.getPid())))
//                    .findFirst();
//                DistrictDTO parent = parentOptional.get();
//                parent.getChildren().add(districtDTO);
//            } else {
//                //没有父节点则直接放入tree
//                tree.add(districtDTO);
//            }
//            //遍历过的节点都放在cacheTreeNodeList
//            treeNodeList.add(districtDTO);
//        }
//        return tree.stream().peek(this::setChildrenNull).collect(Collectors.toList());
//    }
//
//    public void setChildrenNull(DistrictDTO treeNode) {
//        if (treeNode.getChildren() == null || treeNode.getChildren().isEmpty()) {
//            treeNode.setChildren(null);
//        } else {
//            treeNode.setChildren(treeNode.getChildren().stream().peek(this::setChildrenNull).collect(Collectors.toList()));
//        }
//    }
//
//    public List<DistrictDTO> getProvince() {
//        return districtRepository
//            .findAllByDeep(0)
//            .stream()
//            .map(district -> {
//                DistrictDTO districtDTO = new DistrictDTO();
//                districtDTO.setValue(String.valueOf(district.getId()));
//                districtDTO.setLabel(district.getExtName());
//                districtDTO.setChildren(null);
//                return districtDTO;
//            })
//            .collect(Collectors.toList());
//    }
//}

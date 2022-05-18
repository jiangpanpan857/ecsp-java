package com.ruowei.ecsp.repository;

import com.ruowei.ecsp.domain.ForestData;
import com.ruowei.ecsp.web.rest.dto.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

/**
 * Spring Data SQL repository for the ForestData entity.
 */
@Repository
public interface ForestDataRepository extends JpaRepository<ForestData, Long>, QuerydslPredicateExecutor<ForestData> {
    String FORESTDATA_ALL_PROVINCE_STORAGE_BY_YEAR = "forestDataAllProvinceStorageByYear";
    String FORESTDATA_ALL_PROVINCE_AREA_INCREMENT_BY_YEAR = "forestDataAllProvinceAreaIncrementByYear";
    String FORESTDATA_ALL_CITY_STORAGE_BY_PROVINCE_AND_YEAR = "forestDataAllCityStorageByProvinceAndYear";
    String FORESTDATA_ALL_CITY_AREA_INCREMENT_BY_PROVINCE_AND_YEAR = "forestDataAllCityAreaIncrementByProvinceAndYear";
    String FORESTDATA_ALL_PROVINCE_STORAGE_RANKING_BY_YEAR = "forestDataAllProvinceStorageRankingByYear";
    String FORESTDATA_ALL_PROVINCE_AREA_INCREMENT_RANKING_BY_YEAR = "forestDataAllProvinceAreaIncrementRankingByYear";
    String FORESTDATA_ALL_CITY_STORAGE_RANKING_BY_PROVINCE_AND_YEAR = "forestDataAllCityStorageRankingByProvinceAndYear";
    String FORESTDATA_ALL_CITY_AREA_INCREMENT_RANKING_BY_PROVINCE_AND_YEAR = "forestDataAllCityAreaIncrementRankingByProvinceAndYear";
    String FORESTDATA_ALL_YEAR_STORAGE_BY_PROVINCE = "forestDataAllYearStorageByProvince";
    String FORESTDATA_ALL_YEAR_AREA_INCREMENT_BY_PROVINCE = "forestDataAllYearAreaIncrementByProvince";
    String FORESTDATA_ALL_YEAR_STORAGE_BY_CITY = "forestDataAllYearStorageByCity";
    String FORESTDATA_ALL_YEAR_AREA_INCREMENT_BY_CITY = "forestDataAllYearAreaIncrementByCity";

    // 获取各省的年度数值：蓄积量
//    @Cacheable(cacheNames = FORESTDATA_ALL_PROVINCE_STORAGE_BY_YEAR)
    @Query(
        value = "select f.provinceId as id,f.provinceName as name,sum(f.storage) as value from ForestData f where f.year >= ?1 and f.year <= ?2 and (f.cityId is null) group by f.provinceId,f.provinceName order by f.provinceId asc"
    )
    List<ForestDataAllProvinceDataByYearDTO> getAllProvinceStorageByYear(String startYear, String endYear);

    // 获取各省的年度数值：增长面积
//    @Cacheable(cacheNames = FORESTDATA_ALL_PROVINCE_AREA_INCREMENT_BY_YEAR)
    @Query(
        value = "select f.provinceId as id,f.provinceName as name,sum(f.areaIncrement) as value from ForestData f where f.year >= ?1 and f.year <= ?2 and (f.cityId is null) group by f.provinceId,f.provinceName order by f.provinceId asc"
    )
    List<ForestDataAllProvinceDataByYearDTO> getAllProvinceAreaIncrementByYear(String startYear, String endYear);

    // 获取指定省下各市的年度数据：蓄积量
//    @Cacheable(cacheNames = FORESTDATA_ALL_CITY_STORAGE_BY_PROVINCE_AND_YEAR)
    @Query(
        value = "select f.cityId as id,f.cityName as name,sum(f.storage) as value from ForestData f where f.provinceId = ?1 and f.year >= ?2 and f.year <= ?3 and (f.areaId is null) group by f.cityId,f.cityName order by f.cityId asc"
    )
    List<ForestDataAllCityDataByProvinceAndYearDTO> getAllCityStorageByProvinceAndYear(String provinceId, String startYear, String endYear);

    // 获取指定省下各市的年度数据：增长面积
//    @Cacheable(cacheNames = FORESTDATA_ALL_CITY_AREA_INCREMENT_BY_PROVINCE_AND_YEAR)
    @Query(
        value = "select f.cityId as id,f.cityName as name,sum(f.areaIncrement) as value from ForestData f where f.provinceId = ?1 and f.year >= ?2 and f.year <= ?3 and (f.areaId is null) group by f.cityId,f.cityName order by f.cityId asc"
    )
    List<ForestDataAllCityDataByProvinceAndYearDTO> getAllCityAreaIncrementByProvinceAndYear(
        String provinceId,
        String startYear,
        String endYear
    );

    // 获取指定市下各区的年度数据：蓄积量
//    @Cacheable(cacheNames = FORESTDATA_ALL_AREA_STORAGE_BY_CITY_AND_YEAR)
    @Query(
        value = "select f.areaId as id,f.areaName as name,sum(f.storage) as value from ForestData f where f.cityId= ?1 and f.year >= ?2 and f.year <= ?3 and (f.areaId is not null) group by f.areaId,f.areaName order by f.areaId asc"
    )
    List<ForestDataAllAreaDataByCityAndYearDTO> getAllAreaStorageByCityAndYear(String cityId, String startYear, String endYear);

    // 获取指定市下各区的年度数据：增长面积
//    @Cacheable(cacheNames = FORESTDATA_ALL_AREA_AREA_INCREMENT_BY_CITY_AND_YEAR)
    @Query(
        value = "select f.areaId as id,f.areaName as name,sum(f.areaIncrement) as value from ForestData f where f.cityId= ?1 and f.year >= ?2 and f.year <= ?3 and (f.areaId is not null) group by f.areaId,f.areaName order by f.areaId asc"
    )
    List<ForestDataAllAreaDataByCityAndYearDTO> getAllAreaAreaIncrementByCityAndYear(String cityId, String startYear, String endYear);

    // 获取各省的年度数据：蓄积量排名
//    @Cacheable(cacheNames = FORESTDATA_ALL_PROVINCE_STORAGE_RANKING_BY_YEAR)
    @Query(
        value = "select new com.ruowei.ecsp.web.rest.dto.ForestDataAllProvinceRankingByYearDTO(0,f.provinceId,f.provinceName,sum(f.storage)) from ForestData f where f.year >= ?1 and f.year <= ?2 and (f.cityId is null) group by f.provinceId, f.provinceName order by sum(f.storage) desc"
    )
    List<ForestDataAllProvinceRankingByYearDTO> getAllProvinceStorageRankingByYear(String startYear, String endYear);

    // 获取各省的年度数据：增长面积排名
//    @Cacheable(cacheNames = FORESTDATA_ALL_PROVINCE_AREA_INCREMENT_RANKING_BY_YEAR)
    @Query(
        value = "select new com.ruowei.ecsp.web.rest.dto.ForestDataAllProvinceRankingByYearDTO(0,f.provinceId,f.provinceName,sum(f.areaIncrement)) from ForestData f where f.year >= ?1 and f.year <= ?2 and (f.cityId is null) group by f.provinceId, f.provinceName order by sum(f.areaIncrement) desc"
    )
    List<ForestDataAllProvinceRankingByYearDTO> getAllProvinceAreaIncrementRankingByYear(String startYear, String endYear);

    // 获取指定省下各市的年度数据：蓄积量排名
//    @Cacheable(cacheNames = FORESTDATA_ALL_CITY_STORAGE_RANKING_BY_PROVINCE_AND_YEAR)
    @Query(
        value = "select new com.ruowei.ecsp.web.rest.dto.ForestDataAllCityRankingByProvinceAndYearDTO(0,f.cityId,f.cityName,sum(f.storage)) from ForestData f where f.provinceId = ?1 and f.year >= ?2 and f.year <= ?3 and f.areaId is null group by f.cityId, f.cityName order by sum(f.storage) desc"
    )
    List<ForestDataAllCityRankingByProvinceAndYearDTO> getAllCityStorageRankingByProvinceAndYear(
        String provinceId,
        String startYear,
        String endYear
    );

    // 获取指定省下各市的年度数据：增长面积排名
//    @Cacheable(cacheNames = FORESTDATA_ALL_CITY_AREA_INCREMENT_RANKING_BY_PROVINCE_AND_YEAR)
    @Query(
        value = "select new com.ruowei.ecsp.web.rest.dto.ForestDataAllCityRankingByProvinceAndYearDTO(0,f.cityId,f.cityName,sum(f.areaIncrement)) from ForestData f where f.provinceId = ?1 and f.year >= ?2 and f.year <= ?3 and f.areaId is null group by f.cityId, f.cityName order by sum(f.areaIncrement) desc"
    )
    List<ForestDataAllCityRankingByProvinceAndYearDTO> getAllCityAreaIncrementRankingByProvinceAndYear(
        String provinceId,
        String startYear,
        String endYear
    );

    //  获取指定市下各区的年度数据：蓄积量排名
//    @Cacheable(cacheNames = FORESTDATA_ALL_AREA_STORAGE_RANKING_BY_CITY_AND_YEAR)
    @Query(
        value = "select new com.ruowei.ecsp.web.rest.dto.ForestDataAllAreaRankingByCityAndYearDTO(0,f.areaId,f.areaName,sum(f.storage)) from ForestData f where f.cityId = ?1 and f.year >= ?2 and f.year <= ?3 and f.areaId is not null group by f.areaId, f.areaName order by sum(f.storage) desc"
    )
    List<ForestDataAllAreaRankingByCityAndYearDTO> getAllAreaStorageRankingByCityAndYear(String cityId, String startYear, String endYear);

    //  获取指定市下各区的年度数据：增长面积排名
//    @Cacheable(cacheNames = FORESTDATA_ALL_AREA_AREA_INCREMENT_RANKING_BY_CITY_AND_YEAR)
    @Query(
        value = "select new com.ruowei.ecsp.web.rest.dto.ForestDataAllAreaRankingByCityAndYearDTO(0,f.areaId,f.areaName,sum(f.areaIncrement)) from ForestData f where f.cityId = ?1 and f.year >= ?2 and f.year <= ?3 and f.areaId is not null group by f.areaId, f.areaName order by sum(f.areaIncrement) desc"
    )
    List<ForestDataAllAreaRankingByCityAndYearDTO> getAllAreaAreaIncrementRankingByCityAndYear(
        String cityId,
        String startYear,
        String endYear
    );

    // 获取指定省的指定年份数据：蓄积量
//    @Cacheable(cacheNames = FORESTDATA_ALL_YEAR_STORAGE_BY_PROVINCE)
    @Query(value = "select sum(f.storage) from ForestData f where f.provinceId = ?1 and f.year = ?2 and f.cityId is null")
    BigDecimal getAllYearStorageByProvince(String provinceId, String year);

    // 获取指定省的指定年份数据：增长面积
//    @Cacheable(cacheNames = FORESTDATA_ALL_YEAR_AREA_INCREMENT_BY_PROVINCE)
    @Query(value = "select sum(f.areaIncrement) from ForestData f where f.provinceId = ?1 and f.year = ?2 and f.cityId is null")
    BigDecimal getAllYearAreaIncrementByProvince(String provinceId, String year);

    // 获取指定市的指定年份数据：蓄积量
//    @Cacheable(cacheNames = FORESTDATA_ALL_YEAR_STORAGE_BY_CITY)
    @Query(value = "select sum(f.storage) from ForestData f where f.cityId = ?1 and f.year = ?2 and f.areaId is null")
    BigDecimal getAllYearStorageByCity(String cityId, String year);

    // 获取指定市的指定年份数据：增长面积
//    @Cacheable(cacheNames = FORESTDATA_ALL_YEAR_AREA_INCREMENT_BY_CITY)
    @Query(value = "select sum(f.areaIncrement) from ForestData f where f.cityId = ?1 and f.year = ?2 and f.areaId is null")
    BigDecimal getAllYearAreaIncrementByCity(String cityId, String year);

    // TODO 获取指定区的指定年份数据：蓄积量
//    @Cacheable(cacheNames = FORESTDATA_ALL_YEAR_STORAGE_BY_AREA)
    @Query(value = "select sum(f.storage) from ForestData f where f.areaId = ?1 and f.year = ?2")
    BigDecimal getAllYearStorageByArea(String areaId, String year);

    // TODO 获取指定区的指定年份数据：增长面积
//    @Cacheable(cacheNames = FORESTDATA_ALL_YEAR_AREA_INCREMENT_BY_AREA)
    @Query(value = "select sum(f.areaIncrement) from ForestData f where f.areaId = ?1 and f.year = ?2")
    BigDecimal getAllYearAreaIncrementByArea(String areaId, String year);
}

package com.ruowei.ecsp.repository;

import com.ruowei.ecsp.domain.CarbonTrade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data SQL repository for the CarbonTrade entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CarbonTradeRepository extends JpaRepository<CarbonTrade, Long>, QuerydslPredicateExecutor<CarbonTrade> {
    CarbonTrade findFirstByProvinceNameOrderByTradeDateDesc(String provinceName);

    @Query(
        value = "select distinct c.type from CarbonTrade c where 1 = 1 and (c.type is not null)  and (not c.type = '-') and (not c.type = '') order by c.type asc "
    )
    List<String> getAllType();

    @Query(
        nativeQuery = true,
        value = "delete \n" +
        "from carbon_trade t\n" +
        "where \n" +
        "	t.id in \n" +
        "          (select * " +
        "           from \n" +
        "				(select t.id \n" +
        "				 from carbon_trade t \n" +
        "				 where t.id not in \n" +
        "					(select min(t.id) \n" +
        "					 from carbon_trade t \n" +
        "					 group by t.province_name, t.extra_info\n" +
        "					)\n" +
        "				) a \n" +
        "			)"
    )
    @Modifying
    void distinctAll(); // 去重【多个保留一个】
}

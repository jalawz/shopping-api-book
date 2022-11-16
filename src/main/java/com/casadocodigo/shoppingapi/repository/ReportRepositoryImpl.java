package com.casadocodigo.shoppingapi.repository;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.casadocodigo.shoppingapi.dto.ShopReportDTO;
import com.casadocodigo.shoppingapi.model.Shop;

public class ReportRepositoryImpl implements ReportRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Shop> getShopByFilters(Date dataInicio, Date dataFim, Float valorMinimo) {
        StringBuilder sb = generateSqlFilter(dataFim, valorMinimo);

        Query query = generateQuery(dataInicio, dataFim, valorMinimo, sb);

        return query.getResultList();
    }

    @Override
    public ShopReportDTO getReportByDate(Date dataInicio, Date dataFim) {
        StringBuilder sb = new StringBuilder();
        sb.append("select count(sp.id), sum(sp.total), avg(sp.total) ");
        sb.append("from shopping.shop sp ");
        sb.append("where sp.date >= :dataInicio ");
        sb.append("and sp.date <= :dataFim ");

        Query query = entityManager.createNativeQuery(sb.toString(), Shop.class);
        query.setParameter("dataInicio", dataInicio);
        query.setParameter("dataFim", dataFim);

        Object[] result = (Object[]) query.getSingleResult();

        return ShopReportDTO.builder()
            .count((Integer) result[0])
            .total((Double) result[1])
            .mean((Double) result[2])
            .build();
    }

    private Query generateQuery(Date dataInicio, Date dataFim, Float valorMinimo, StringBuilder sb) {
        Query query = entityManager.createQuery(sb.toString());
        query.setParameter("dataInicio", dataInicio);

        if (Objects.nonNull(dataFim)) {
            query.setParameter("dataFim", dataFim);
        }

        if (Objects.nonNull(valorMinimo)) {
            query.setParameter("valorMinimo", valorMinimo);
        }
        return query;
    }

    private StringBuilder generateSqlFilter(Date dataFim, Float valorMinimo) {
        StringBuilder sb = new StringBuilder();
        sb.append("select s ");
        sb.append("from shop s ");
        sb.append("where s.date >= :dataInicio");

        if (Objects.nonNull(dataFim)) {
            sb.append("and s.date <= :dataFim ");
        }

        if (Objects.nonNull(valorMinimo)) {
            sb.append("and s.total <= :valorMinimo");
        }
        return sb;
    }
    
}

package solveryinvest.stocks.filters;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;
import solveryinvest.stocks.entity.Asset;
import solveryinvest.stocks.exception.NotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class AssetFilter {

    private static final String NAME = "name";
    private static final String FIGI = "figi";
    private static final String TICKER = "ticker";

    public static Specification<Asset> getSpecificationFromFilters(List<FilterList> filterLists) {
        Specification<Asset> specification = Specification.where(null);
        if (Objects.isNull(filterLists) || filterLists.isEmpty()) {
            return specification;
        }
        for (FilterList filterList : filterLists) {
            final FilterMergingType type = filterList.getType();
            final List<Filter> filters = filterList.getFilters();
            Specification<Asset> sp = Specification.where(null);
            for (Filter filter : filters) {
                if (type.equals(FilterMergingType.OR)) {
                    sp = sp.or(createSpecification(filter));
                } else {
                    sp = sp.and(createSpecification(filter));
                }
            }
            specification = specification.and(sp);
        }
        return specification;
    }

    private static Specification<Asset> createSpecification(Filter filter) {
        return switch (filter.getType()) {
            case SEARCH -> ((root, query, criteriaBuilder) -> fullSearch(filter, root, criteriaBuilder));
            default -> throw new NotFoundException("Operation not supported yet");
        };
    }

    private static Predicate fullSearch(Filter filter, Root<Asset> root, CriteriaBuilder cb) {
        var strings = filter.getValue().trim().toLowerCase().split("\\s+");
        final var predicates = new ArrayList<>();
        for (var value : strings) {
            predicates.add(cb.like(cb.lower(root.get(NAME)), "%" + value + "%"));
            predicates.add(cb.like(cb.lower(root.get(FIGI)), "%" + value + "%"));
            predicates.add(cb.like(cb.lower(root.get(TICKER)), "%" + value + "%"));

        }
        return cb.or(predicates.toArray(new Predicate[0]));
    }
}

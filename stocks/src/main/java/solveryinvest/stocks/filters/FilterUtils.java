package solveryinvest.stocks.filters;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Supplier;

import static java.time.ZoneOffset.UTC;

public class FilterUtils {

    private static final String SURNAME = "surname";
    private static final String NAME = "name";
    private static final String PATRONYMIC = "patronymic";

    public static Pageable getSort(Integer page, Integer pageSize, SortType direction, Supplier<String> dateCreated) {
        Sort sort = Sort.unsorted();
        String fieldStr = dateCreated.get();

        if (Objects.equals(direction, SortType.ASC)) {
            if (Objects.equals(SURNAME, fieldStr)) {
                sort = sortForOfficials(sort, SortType.ASC);
            } else {
                sort = sort.and(Sort.by(Sort.Order.by(fieldStr).ignoreCase()).ascending());
            }

        }
        if (Objects.equals(direction, SortType.DESC)) {
            if (Objects.equals(SURNAME, fieldStr)) {
                sort = sortForOfficials(sort, SortType.DESC);
            } else {
                sort = sort.and(Sort.by(Sort.Order.by(fieldStr).ignoreCase()).descending());
            }
        }
        return PageRequest.of(page, pageSize, sort);
    }

    public static Object castToRequiredType(Class fieldType, List<String> value) {
        List<Object> lists = new ArrayList<>();
        for (var s : value) {
            lists.add(castToRequiredType(fieldType, s));
        }
        return lists;
    }

    public static Object castToRequiredType(Class fieldType, String value) {
        if (fieldType.isAssignableFrom(Double.class)) {
            return Double.valueOf(value);
        } else if (fieldType.isAssignableFrom(Integer.class)) {
            return Integer.valueOf(value);
        } else if (fieldType.isAssignableFrom(Long.class)) {
            return Long.valueOf(value);
        } else if (Enum.class.isAssignableFrom(fieldType)) {
            if (value.chars().allMatch(Character::isDigit)) {
                return fieldType.getEnumConstants()[Integer.parseInt(value)];
            }
            return Enum.valueOf(fieldType, value);
        } else if (fieldType.isAssignableFrom(Boolean.class)) {
            return Boolean.valueOf(value);
        } else if (fieldType.isAssignableFrom(String.class)) {
            return value;
        } else if (fieldType.isAssignableFrom(Float.class)) {
            return Float.valueOf(value);
        }
        return null;
    }

    public static Float castToRequiredType(Class fieldType, Long value) {
        if (fieldType.isAssignableFrom(Float.class)) {
            return Float.valueOf(value);
        }
        return null;
    }

    public static OffsetDateTime castDateRequiredType(Class fieldType, Long value) {
        if (fieldType.isAssignableFrom(OffsetDateTime.class)) {
            return OffsetDateTime.ofInstant(Instant.ofEpochMilli(value), UTC);
        }
        return null;
    }

    public static boolean tryToCast(String value) {
        try {
            Long.parseLong(value);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private static Sort sortForOfficials(Sort sort, SortType direction) {
        if (Objects.equals(direction, SortType.ASC)) {
            sort = sort.and(Sort.by(Sort.Order.by(SURNAME).ignoreCase()).ascending())
                    .and(Sort.by(Sort.Order.by(NAME).ignoreCase()).ascending())
                    .and(Sort.by(Sort.Order.by(PATRONYMIC).ignoreCase()).ascending());
        } else {
            sort = sort.and(Sort.by(Sort.Order.by(SURNAME).ignoreCase()).descending())
                    .and(Sort.by(Sort.Order.by(NAME).ignoreCase()).ascending())
                    .and(Sort.by(Sort.Order.by(PATRONYMIC).ignoreCase()).ascending());
        }
        return sort;
    }
}

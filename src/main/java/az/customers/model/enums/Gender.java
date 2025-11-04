package az.customers.model.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Gender {

    MALE(1), FEMALE(2), OTHER(0);

    private final Integer value;

    public static Gender byValue(Integer value) {
        for (Gender gender : Gender.values()) {
            if (gender.getValue().equals(value)) {
                return gender;
            }
        }
        return OTHER;
    }
}

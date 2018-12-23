package org.owasp.pangloss.domain;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class UserTest {

    @Test
    public void should_create_user() {
        //When
        String me = "me";
        User user = new User(me);

        //Then
        assertThat(user.getName()).isEqualTo(me);
    }

    @Test
    public void should_not_be_able_to_create_user_from_null_name() {
        assertThatThrownBy(() -> new User(null))
                .hasMessage("name cannot be null")
                .isInstanceOf(NullPointerException.class);
    }
}
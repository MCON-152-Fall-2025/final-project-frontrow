package com.mcon152.recipeshare.domain;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("AppUser Domain Tests")
class AppUserTest {

    @Nested
    @DisplayName("Constructor Tests")
    class ConstructorTests {

        @Test
        @DisplayName("Default constructor creates empty AppUser")
        void defaultConstructor() {
            AppUser user = new AppUser();
            assertNotNull(user);
            assertNull(user.getId());
            assertNull(user.getUsername());
            assertNull(user.getPassword());
            assertNull(user.getDisplayName());
        }

        @Test
        @DisplayName("Constructor with all fields sets values correctly")
        void constructorWithAllFields() {
            AppUser user = new AppUser(1L, "john_doe", "secret123", "John Doe");

            assertEquals(1L, user.getId());
            assertEquals("john_doe", user.getUsername());
            assertEquals("secret123", user.getPassword());
            assertEquals("John Doe", user.getDisplayName());
        }

        @Test
        @DisplayName("Constructor without ID sets values correctly")
        void constructorWithoutId() {
            AppUser user = new AppUser("jane_doe", "pass456", "Jane Doe");

            assertNull(user.getId());
            assertEquals("jane_doe", user.getUsername());
            assertEquals("pass456", user.getPassword());
            assertEquals("Jane Doe", user.getDisplayName());
        }

        @Test
        @DisplayName("Constructor handles null displayName")
        void constructorWithNullDisplayName() {
            AppUser user = new AppUser("user123", "password", null);

            assertEquals("user123", user.getUsername());
            assertEquals("password", user.getPassword());
            assertNull(user.getDisplayName());
        }
    }

    @Nested
    @DisplayName("Getter and Setter Tests")
    class GetterSetterTests {

        @Test
        @DisplayName("setUsername and getUsername work correctly")
        void usernameGetterSetter() {
            AppUser user = new AppUser();
            user.setUsername("test_user");
            assertEquals("test_user", user.getUsername());
        }

        @Test
        @DisplayName("setPassword and getPassword work correctly")
        void passwordGetterSetter() {
            AppUser user = new AppUser();
            user.setPassword("myPassword123");
            assertEquals("myPassword123", user.getPassword());
        }

        @Test
        @DisplayName("setDisplayName and getDisplayName work correctly")
        void displayNameGetterSetter() {
            AppUser user = new AppUser();
            user.setDisplayName("Test User");
            assertEquals("Test User", user.getDisplayName());
        }

        @Test
        @DisplayName("setId inherited from BaseEntity works correctly")
        void idGetterSetter() {
            AppUser user = new AppUser();
            user.setId(100L);
            assertEquals(100L, user.getId());
        }

        @Test
        @DisplayName("Can update all fields after creation")
        void updateAllFields() {
            AppUser user = new AppUser("old_user", "old_pass", "Old Name");

            user.setId(5L);
            user.setUsername("new_user");
            user.setPassword("new_pass");
            user.setDisplayName("New Name");

            assertEquals(5L, user.getId());
            assertEquals("new_user", user.getUsername());
            assertEquals("new_pass", user.getPassword());
            assertEquals("New Name", user.getDisplayName());
        }
    }

    @Nested
    @DisplayName("Equals and HashCode Tests")
    class EqualsHashCodeTests {

        @Test
        @DisplayName("Two users with same ID and username are equal")
        void equalUsers() {
            AppUser user1 = new AppUser(1L, "john", "pass1", "John");
            AppUser user2 = new AppUser(1L, "john", "pass2", "Johnny");

            assertEquals(user1, user2);
            assertEquals(user1.hashCode(), user2.hashCode());
        }

        @Test
        @DisplayName("Same object reference is equal to itself")
        void sameObjectEquals() {
            AppUser user = new AppUser(1L, "john", "pass", "John");
            assertSame(user, user);
            assertEquals(user, user);
        }

        @Test
        @DisplayName("User is not equal to null")
        void notEqualToNull() {
            AppUser user = new AppUser(1L, "john", "pass", "John");
            assertNotNull(user);
            assertFalse(user.equals(null));
        }

        @Test
        @DisplayName("User is not equal to different class")
        void notEqualToDifferentClass() {
            AppUser user = new AppUser(1L, "john", "pass", "John");
            String notAUser = "not a user";
            assertFalse(user.equals(notAUser));
        }

        @Test
        @DisplayName("Two users with different usernames are not equal")
        void differentUsernames() {
            AppUser user1 = new AppUser(1L, "john", "pass", "John");
            AppUser user2 = new AppUser(1L, "jane", "pass", "John");

            assertNotEquals(user1, user2);
        }

        @Test
        @DisplayName("Two users with different IDs but same username are not equal")
        void differentIds() {
            AppUser user1 = new AppUser(1L, "john", "pass", "John");
            AppUser user2 = new AppUser(2L, "john", "pass", "John");

            assertNotEquals(user1, user2);
        }

        @Test
        @DisplayName("Two users with null IDs but same username are equal")
        void nullIdsButSameUsername() {
            AppUser user1 = new AppUser("john", "pass1", "John");
            AppUser user2 = new AppUser("john", "pass2", "Johnny");

            assertEquals(user1, user2);
            assertEquals(user1.hashCode(), user2.hashCode());
        }

        @Test
        @DisplayName("Users with null usernames and same ID are equal")
        void nullUsernames() {
            AppUser user1 = new AppUser();
            user1.setId(1L);
            AppUser user2 = new AppUser();
            user2.setId(1L);

            assertEquals(user1, user2);
        }
    }

    @Nested
    @DisplayName("ToString Tests")
    class ToStringTests {

        @Test
        @DisplayName("toString includes all important fields")
        void toStringIncludesFields() {
            AppUser user = new AppUser(1L, "john_doe", "secret", "John Doe");
            String result = user.toString();

            assertTrue(result.contains("AppUser"));
            assertTrue(result.contains("id=1"));
            assertTrue(result.contains("username='john_doe'"));
            assertTrue(result.contains("displayName='John Doe'"));
            // Password should not be in toString for security
            assertFalse(result.contains("secret"));
        }

        @Test
        @DisplayName("toString handles null values gracefully")
        void toStringWithNulls() {
            AppUser user = new AppUser();
            String result = user.toString();

            assertNotNull(result);
            assertTrue(result.contains("AppUser"));
            assertTrue(result.contains("null"));
        }

        @Test
        @DisplayName("toString includes inherited timestamp fields")
        void toStringIncludesTimestamps() {
            AppUser user = new AppUser(1L, "john", "pass", "John");
            String result = user.toString();

            assertTrue(result.contains("createdAt="));
            assertTrue(result.contains("updatedAt="));
        }
    }

    @Nested
    @DisplayName("BaseEntity Inheritance Tests")
    class BaseEntityInheritanceTests {

        @Test
        @DisplayName("AppUser extends BaseEntity")
        void extendsBaseEntity() {
            AppUser user = new AppUser();
            assertInstanceOf(BaseEntity.class, user);
        }

        @Test
        @DisplayName("Can access BaseEntity methods")
        void accessBaseEntityMethods() {
            AppUser user = new AppUser("john", "pass", "John");

            // Test inherited methods
            user.setId(10L);
            assertEquals(10L, user.getId());

            LocalDateTime now = LocalDateTime.now();
            user.setCreatedAt(now);
            assertEquals(now, user.getCreatedAt());

            user.setUpdatedAt(now);
            assertEquals(now, user.getUpdatedAt());
        }

        @Test
        @DisplayName("CreatedAt and UpdatedAt are initially null")
        void timestampsInitiallyNull() {
            AppUser user = new AppUser("john", "pass", "John");

            // Timestamps will be null until persisted by JPA
            assertNull(user.getCreatedAt());
            assertNull(user.getUpdatedAt());
        }
    }

    @Nested
    @DisplayName("Business Logic Tests")
    class BusinessLogicTests {

        @Test
        @DisplayName("Username can be updated")
        void updateUsername() {
            AppUser user = new AppUser("old_username", "pass", "User");
            user.setUsername("new_username");
            assertEquals("new_username", user.getUsername());
        }

        @Test
        @DisplayName("Password can be changed")
        void changePassword() {
            AppUser user = new AppUser("user", "old_pass", "User");
            user.setPassword("new_pass");
            assertEquals("new_pass", user.getPassword());
        }

        @Test
        @DisplayName("DisplayName is optional and can be null")
        void displayNameOptional() {
            AppUser user = new AppUser("user", "pass", "Name");
            user.setDisplayName(null);
            assertNull(user.getDisplayName());
        }

        @Test
        @DisplayName("Can create user with minimal information")
        void minimalUser() {
            AppUser user = new AppUser();
            user.setUsername("user");
            user.setPassword("pass");

            assertEquals("user", user.getUsername());
            assertEquals("pass", user.getPassword());
            assertNull(user.getDisplayName());
        }
    }

    @Nested
    @DisplayName("Edge Cases")
    class EdgeCaseTests {

        @Test
        @DisplayName("Empty string username is allowed")
        void emptyUsername() {
            AppUser user = new AppUser("", "pass", "User");
            assertEquals("", user.getUsername());
        }

        @Test
        @DisplayName("Empty string password is allowed")
        void emptyPassword() {
            AppUser user = new AppUser("user", "", "User");
            assertEquals("", user.getPassword());
        }

        @Test
        @DisplayName("Very long displayName is allowed")
        void longDisplayName() {
            String longName = "A".repeat(100);
            AppUser user = new AppUser("user", "pass", longName);
            assertEquals(longName, user.getDisplayName());
        }

        @Test
        @DisplayName("Special characters in username are allowed")
        void specialCharactersInUsername() {
            AppUser user = new AppUser("user@example.com", "pass", "User");
            assertEquals("user@example.com", user.getUsername());
        }

        @Test
        @DisplayName("Unicode characters in displayName are supported")
        void unicodeDisplayName() {
            AppUser user = new AppUser("user", "pass", "用户名 José María");
            assertEquals("用户名 José María", user.getDisplayName());
        }
    }
}

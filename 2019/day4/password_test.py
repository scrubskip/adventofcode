import unittest
import password


class PasswordTestCase(unittest.TestCase):
    def test_simple(self):
        self.assertTrue(password.is_valid_password(111111))
        self.assertFalse(password.is_valid_password(223450))
        self.assertFalse(password.is_valid_password(123789))

    def test_max_2(self):
        self.assertFalse(password.is_valid_password_max_2(111111))
        self.assertTrue(password.is_valid_password_max_2(112233))
        self.assertFalse(password.is_valid_password_max_2(123444))
        self.assertTrue(password.is_valid_password_max_2(111122))


if __name__ == '__main__':
    unittest.main()

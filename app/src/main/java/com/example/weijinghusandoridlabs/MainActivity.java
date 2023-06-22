package com.example.weijinghusandoridlabs;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

    /**
     * This class is used to take action of the week5 activity layout, when the user input the password tp try to login in
     * it should check if this string has an Upper Case letter, a lower case letter, a number, and a special symbol (#$%^&*!@?).
     * If it is missing any of these 4 requirements, then show a Toast message saying which requirement is missing,
     * like  "Your password meets the requirements". Otherwise, set the TextView to "You shall not pass!".
     * @author Weijing Hu
     * @version 1.0
     */
    public class MainActivity extends AppCompatActivity {

                /**
                 * This holds the text that the user input
                 */
                private EditText editTextPassword =null;
        /**
                 * This holds the text at the centre of the screen
                 */
                private TextView textViewResult = null;

                @SuppressLint("SetTextI18n")
                @Override
                protected void onCreate(Bundle savedInstanceState) {
                    super.onCreate(savedInstanceState);
                    setContentView(R.layout.activity_main);

                    editTextPassword = findViewById(R.id.editTextPassword);
                    // This holds the button of the login
                    Button buttonLogin = findViewById(R.id.buttonLogin);
                    textViewResult = findViewById(R.id.textView);

                    buttonLogin.setOnClickListener(clk -> {
                        String password = editTextPassword.getText().toString();
                        boolean isComplexPassword = checkPasswordComplexity(password);

                        if (isComplexPassword) {
                            textViewResult.setText("Your password meets the requirements");
                        } else {
                            textViewResult.setText("You shall not pass!");
                        }

                    });
                }

                /**
                 * This function check if this string has an Upper Case letter, a lower case letter, a number, and a special symbol (#$%^&*!@?).
                 * If it is missing any of these 4 requirements, then show a Toast message saying which requirement is missing,
                 * like "Your password does not have an upper case letter", or "Your password does not have a special symbol".
                 * @param password user input a String that is being checked
                 * @return return true if the password is complex enough, and false if it is not complex enough.
                 */
                private boolean checkPasswordComplexity(String password) {
                    boolean foundUpperCase, foundLowerCase, foundNumber, foundSpecial;
                    foundUpperCase = foundLowerCase = foundNumber = foundSpecial = false;

                    for (char c : password.toCharArray()) {
                        if (Character.isUpperCase(c)) {
                            foundUpperCase = true;
                        } else if (Character.isLowerCase(c)) {
                            foundLowerCase = true;
                        } else if (Character.isDigit(c)) {
                            foundNumber = true;
                        } else if (isSpecialCharacter(c)) {
                            foundSpecial = true;
                        }
                    }

                    if (!foundUpperCase) {
                        Toast.makeText(MainActivity.this, "Your password does not have an upper case letter", Toast.LENGTH_SHORT).show();
                        return false;
                    } else if (!foundLowerCase) {
                        Toast.makeText(MainActivity.this, "Your password does not have a lower case letter", Toast.LENGTH_SHORT).show();
                        return false;
                    } else if (!foundNumber) {
                        Toast.makeText(MainActivity.this, "Your password does not have a number", Toast.LENGTH_SHORT).show();
                        return false;
                    } else if (!foundSpecial) {
                        Toast.makeText(MainActivity.this, "Your password does not have a special symbol", Toast.LENGTH_SHORT).show();
                        return false;
                    } else {
                        return true; //only get here if they're all true
                    }
                }

                /**
                 * This function check if there's special character like #$%^&*!@?
                 * If the char c is not one of the cases, then it will return false.
                 * @param c a char of the user input
                 * @return will return true since there's no break; return false if char c is not one of the cases
                 */
                private boolean isSpecialCharacter(char c) {
                            switch (c) {
                                case '#':
                                case '$':
                                case '%':
                                case '^':
                                case '&':
                                case '*':
                                case '!':
                                case '@':
                                    return true;
                                default:
                                    return false;
                            }
                    }

        }
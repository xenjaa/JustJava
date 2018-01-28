package com.example.android.justjava;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {
    int quantity = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {
        CheckBox whippedCreamCheckBox = findViewById(R.id.checkbox_whipped);
        boolean hasWhippedCream = whippedCreamCheckBox.isChecked();

        CheckBox chocoCheckBox = findViewById(R.id.checkbox_choco);
        boolean hasChoco = chocoCheckBox.isChecked();

        EditText customerName = findViewById(R.id.name_field);
        String personName = customerName.getText().toString();

        int price = calculatePrice(hasWhippedCream, hasChoco);

        // How to set up a new email intent
        Intent intent = new Intent(Intent.ACTION_SENDTO);

        //This line ensures only email apps are used as opposed to text or messenger apps being included.
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this

        // Optional line of code to add text to the subject line of email
        intent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.order_subject)+ " " + personName);

        // Optional line of code to add text to the body of the email
        intent.putExtra(Intent.EXTRA_TEXT, createOrderSummary(price,hasWhippedCream, hasChoco, personName));

        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);


        }
    }

    /**
     * Calculates the price of the order.
     * @param hasChoco whether or not Chocolate is added to the order
     * @param hasWhippedCream whether or not Whipped Cream is added to the order
     * @return total price
     */
    private int calculatePrice(boolean hasWhippedCream, boolean hasChoco) {
        // price of 1 cup of coffee
        int pricePerCup = 5;

        // add 1$ if Whipped Cream is added
        if (hasWhippedCream) {
            pricePerCup = pricePerCup + 1;}

        // add 2$ if Choco is added
        if (hasChoco) {
            pricePerCup = pricePerCup + 2;}

        // calculating the total order price by multiplying by quantity
        return pricePerCup = quantity * pricePerCup;
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void displayQuantity(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }

    //this method increments the quantity when the plus button is clicked
    public void increment(View view) {
        if (quantity < 100) {
            quantity++;
            displayQuantity(quantity);
        } else {
            Toast.makeText(this, getString(R.string.toast_increment), Toast.LENGTH_SHORT).show();
        }
    }

    //this method decrements the quantity when the plus button is clicked
    public void decrement(View view) {
        if (quantity > 1) {
            quantity -= 1;
            displayQuantity(quantity);
        } else {Toast.makeText(this, getString(R.string.toast_decrement), Toast.LENGTH_SHORT).show();}

    }

    /**
     * Create summary of the order.
     *
     * @param price of the order
     * @return text summary
     */
    public String createOrderSummary(int price, boolean hasWhippedCream, boolean hasChoco, String personName) {
        String priceMessage = personName;
        priceMessage += "\n" + getString(R.string.whippedcream) + " " + hasWhippedCream;
        priceMessage += "\n" + getString(R.string.choco) +" " + hasChoco;
        priceMessage += "\n" + getString(R.string.quantity);
        priceMessage += "\n" + getString(R.string.total) + " " + price;
        priceMessage += "\n" + getString(R.string.thank_you);
        return priceMessage;
    }
}
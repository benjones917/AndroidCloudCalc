package com.ben.calculator;

import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.ben.calculator.CalculatorActivity;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;


public class ResultsActivity extends CalculatorActivity {
	
	List<ParseObject> parseToAL;
	ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("CloudCalc");
	ParseObject CloudCalc = new ParseObject("CloudCalc");
	
	public void backClicked(View view) {
		Intent myIntent = new Intent(ResultsActivity.this, CalculatorActivity.class);
		ResultsActivity.this.startActivity(myIntent);
	}
	
	
	public void delAllClicked(View view) {
		
		try {
			parseToAL = query.find();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		for (ParseObject CloudCalc : parseToAL) {
			try {
				CloudCalc.deleteAll(parseToAL);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		
		ListView resultsList = (ListView) findViewById(R.id.listView1);
		passedResult.clear();
		resultsList.setAdapter(null);
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_results);
		
		ListView resultsList = (ListView) findViewById(R.id.listView1);
		
		passedResult.clear();
		resultsList.setAdapter(null);
		
		query.orderByDescending("_created_at");
		
		try {
			parseToAL = query.find();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		for (ParseObject CloudCalc : parseToAL) {
			passedResult.add((String) CloudCalc.get("result"));
		}
		
		ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, passedResult);
		resultsList.setAdapter(arrayAdapter);
		
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.results, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}

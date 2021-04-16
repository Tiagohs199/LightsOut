import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;


public class LightsOut extends JFrame implements ActionListener{

	private static final long serialVersionUID = 1L;
	public static void main(String[] args) 
	{
		LightsOut light = new LightsOut();		//Creates a new LightsOut object, which is a new game.
		light.setVisible(true);					//sets the LightsOut object to visible.

	}
	
	//private GameBoard boardLayout;
	private JButton[][] gameButtons;					//this two dimensional array will hold a reference the all of the buttons on the board.
	private JLabel wins;								//a label used to tell the user how many wins they currently have.
	private int winCount;								//a count variable to hold the current number of wins.
	public LightsOut()
	{
		winCount = 0;									//sets the winCount variable to 0.
		//Terminates the program when the user closes the JFrame.
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//Sets title and size of the JFrame.
		setTitle("Lights Out");
		setSize(500, 500);
		
		JPanel mainPanel = new JPanel();				//The current panel that everything is placed in.
		
		mainPanel.setLayout(new BorderLayout());		//sets the layout of the mainPanel.
		
		//Buttons
		JPanel buttonPanel = new JPanel();				//a new panel to hold all of the buttons on the game board.
		gameButtons = new JButton[4][4];				//creates a new two dimensional array that will hold 25 buttons.
		buttonPanel.setLayout(new GridLayout(4,4));		//sets the layout for the buttons.					
		
		for(int i = 0; i<4; i++)						//these two loops will add each button into the two dimensional array.
		{
			for(int j = 0; j<4; j++)
			{
				int random = (int)(Math.random()*3);	//sets up a variable for a random number, so the lights(buttons) will randomly be turned on.
				JButton button = new JButton();			//creats a new JButton object.
				gameButtons[i][j] = button;				//adds the button to the array.
				button.setName(""+i+j);					//sets up the name of each button accordingly where they appear on the game board.
				button.setBackground(Color.BLACK);		//sets each button to black, turned off.
				if(random == 2)							//uses the random variable to change the color of the button to yellow, turned on.
				{
					backgroundColor(button);
				}
				button.addActionListener(this);			//adds an actionlistener to each button.
				buttonPanel.add(button);				//adds the button to the buttonPanel.
			
			}
		}
		
		mainPanel.add(buttonPanel, "Center");			//adds the buttonPanel to the mainPanel.

		setContentPane(mainPanel);						//sets the content pane of the frame.
		}
	public void actionPerformed(ActionEvent e) 
	{
		
		JButton button = (JButton)e.getSource();		//gets the current button that was clicked.
		String location = button.getName();				//gets the name of the current button
		
		char colChar = location.charAt(0);				//gets the char character a position 0 of the button name.
		char rowChar = location.charAt(1);				//gets the char character a position 1 of the button name.
		int col = Character.getNumericValue(colChar);	//this int converted from the char from pos 0 will be used a column indicator.
		int row = Character.getNumericValue(rowChar);	//this int converted from the char from pos 0 will be used a row indicator.
		
		//temporary buttons for the adjacent locations 
		//next to the selected button.
		JButton tempSelected = new JButton();			//a temp button for the selected button
		JButton tempTop = new JButton();				//a temp button for the button above the selected button
		JButton tempLeft = new JButton();				//a temp button for the button left to the selected button
		JButton tempRight = new JButton();				//a temp button for the button right to the selected button
		JButton tempBottom = new JButton();				//a temp button for the button below the selected button
		
		tempSelected = gameButtons[col][row];			//gets the current button selected and stores it in the temp.
		backgroundColor(tempSelected);					//calls the backgroundColor method to change the color of the button.
		
		//each button is attempted, but if it exceeds the Array, then it is caught and nothing is done.
		try
		{
			tempTop = gameButtons[col-1][row];			//get the button that is above the selected button and stores it in temp.
			backgroundColor(tempTop);					//calls the backgroundColor method to change the color of the button.
		}
		catch(ArrayIndexOutOfBoundsException i)
		{
			
		}
		
		try
		{
			tempLeft = gameButtons[col][row-1];			//get the button that is left to the selected button and stores it in temp.
			backgroundColor(tempLeft);					//calls the backgroundColor method to change the color of the button.
		}
		catch(ArrayIndexOutOfBoundsException i)
		{
			
		}
		try
		{
			tempRight = gameButtons[col][row+1];		//get the button that is right to the selected button and stores it in temp.
			backgroundColor(tempRight);					//calls the backgroundColor method to change the color of the button.
		}
		catch(ArrayIndexOutOfBoundsException i)
		{
			
		}
		try
		{
			tempBottom = gameButtons[col+1][row];		//get the button that is below the selected button and stores it in temp.
			backgroundColor(tempBottom);				//calls the backgroundColor method to change the color of the button.
		}
		catch(ArrayIndexOutOfBoundsException i)
		{
			
		}
	
		isWon();										//the isWon method is called to see whether or not the game has been won,
														//in other words if all of the lights have been turned off.
	}
	

	/**
	 * Changes the color of the button sent in
	 * as a parameter to either yellow or black, 
	 * depending on what the current color of the
	 * parameter is.
	 * @param b JButton object.
	 */
	private void backgroundColor(JButton b)
	{
		if(b.getBackground()==Color.BLACK)			//the button b is black, then it is changed to yellow, otherwise it is
		{											//changed to black.
			b.setBackground(Color.YELLOW);
		}
		else
		{
			b.setBackground(Color.BLACK);
		}
	}
	/**
	 * This method is used to determine
	 * if the game board is in a winning
	 * state.
	 */
	private void isWon()
	{
		int count = 0;						//a count variable.
		for(JButton b[]: gameButtons)		//moves through the two dimensional array for each
		{									//JButton.
			for(JButton c: b)
			{
				if(c.getBackground()==Color.BLACK)		//if the current JButton color is black, then the count is incremented.
				{
					count++;
				}
			}
		}
		if(count == 25)									//if the count equals 25 then the game board is in a winning state.
		{
			JOptionPane.showMessageDialog(this, "Congratulations, you have won!");		//user notified the game has been won.
			winCount++;																	//winCount variable incremented by 1.
			wins.setText("Wins: " + winCount);											//sets status of current wins to the user.
			restart();																	//resets the current game board after the game has been won.
		}	
	}
	/**
	 * This method is used to
	 * restart the current game board and
	 * utilizes the backgroundColor method.
	 * 
	 */
	private void restart()
	{
		for(JButton b[]: gameButtons)					//moves through the two dimensional array for each JButton.
		{
			for(JButton c : b)
			{
				int random = (int)(Math.random()*4);	//randomly generates a number between 0 and 3.
				if(random == 2)							//if the number is two, the current JButton color is changed.
				{
					backgroundColor(c);
				}
				
			}
		}
	}
}




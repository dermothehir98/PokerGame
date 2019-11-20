import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage; 
import javafx.geometry.Insets; 
import javafx.geometry.Pos;
import javafx.geometry.HPos;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import java.util.*;
import javafx.scene.control.TextField;
import javafx.scene.control.ScrollPane;

public class fiveCard extends Application {
	Scene scene1;
	static boolean betting = false;
	static TextField textField;
	static Label prompt;
	static Label potLabel;
	static Label yourMoney;
	static int money;
	static int potMoney;
	static Button betButton;
	static Button callButton;
    static Button checkButton;
	static Button raiseButton;
    static Button foldButton;
	static ArrayList<String> deck;
	static int whosTurn;
	static String player2Has;
	static String[] player2Hand;
	static String player3Has;
	static String[] player3Hand;
	static String player4Has;
	static String[] player4Hand;
	static String lastBet;
	static int player2Money;
	static Label player2MoneyLabel;
	static int player3Money;
	static Label player3MoneyLabel;
	static int player4Money;
	static Label player4MoneyLabel;
	static ScrollPane sp;
	static VBox content;
	static int lastBetFigure;
	static int yourDebt;
	static int player2Debt;
	static int player3Debt;
	static int player4Debt;
	static GridPane pane;
	static String[] yourHand;
	static Label card1;
	static Label card2;
	static Label card3;
	static Label card4;
	static Label card5;
	static Label youHaveLabel;
	static boolean youFold;
	static boolean player2Fold;
	static boolean player3Fold;
	static boolean player4Fold;
	static String[] player2Remove;
	static String[] player3Remove;
	static String[] player4Remove;
	static boolean swappedCards;
	static Label player2;
	static Label player3;
	static Label player4;
	static String youHave;
	static boolean loopedOnce;
	
    @Override
    public void start(Stage primaryStage) throws Exception{
        primaryStage.setTitle("Five Card Draw");

		whosTurn = 1;
		lastBet = "none";
		lastBetFigure = 0;
		yourDebt = 0;
		player2Debt = 0;
		player3Debt = 0;
		player4Debt = 0;
		youFold = false;
		player2Fold = false;
		player3Fold = false;
		player4Fold = false;
		swappedCards = false;
		loopedOnce = false;
        betButton = new Button("Bet");
		callButton = new Button("Call");
        checkButton = new Button("Check");
		raiseButton = new Button("Raise");
        foldButton = new Button("Fold");
		
        foldButton.setOnAction(e -> Platform.exit());
		betButton.setOnAction(e -> betEvent("bet"));
		raiseButton.setOnAction(e -> betEvent("raise"));
		checkButton.setOnAction(e -> checkEvent());
		callButton.setOnAction(e -> callEvent());
		
		BorderPane display = new BorderPane();
		BorderPane border1 = new BorderPane();
		display.setBottom(border1);
		
		pane = new GridPane();
		pane.setAlignment(Pos.CENTER);
		pane.setHgap(5);
		pane.setVgap(5);
		pane.setPadding(new Insets(25));
		border1.setCenter(pane);
		
		betButton.setMinWidth(80);
		callButton.setMinWidth(80);
		checkButton.setMinWidth(80);
		raiseButton.setMinWidth(80);
		foldButton.setMinWidth(80);
		
		callButton.setDisable(true);
		raiseButton.setDisable(true);

		deck = makeDeck();
		yourHand = hand();
		player2Hand = hand();
		player3Hand = hand();
		player4Hand = hand();
		String[] yourArray = checkHand(yourHand);
		youHave = yourArray[yourArray.length-1];
		String[] player2Array = checkHand(player2Hand);
		player2Has = player2Array[player2Array.length-1];
		player2Remove = Arrays.copyOf(player2Array, player2Array.length-1);
		String[] player3Array = checkHand(player3Hand);
		player3Has = player3Array[player3Array.length-1];
		player3Remove = Arrays.copyOf(player3Array, player3Array.length-1);
		String[] player4Array = checkHand(player4Hand);
		player4Has = player4Array[player4Array.length-1];
		player4Remove = Arrays.copyOf(player4Array, player4Array.length-1);
		formatHand(yourHand);
		
		for (int i = 0;i < 5;i++)
			System.out.println(player2Hand[i]);
		System.out.println(player2Has + "\n");
		for (int i = 0;i < 5;i++)
			System.out.println(player3Hand[i]);
		System.out.println(player3Has + "\n");
		for (int i = 0;i < 5;i++)
			System.out.println(player4Hand[i]);
		System.out.println(player4Has + "\n");
		
		youHaveLabel = new Label(youHave);
		youHaveLabel.setMinWidth(Region.USE_PREF_SIZE);
		
		money = 1000;
		yourMoney = new Label("Money = $" + money);
		yourMoney.setMinWidth(Region.USE_PREF_SIZE);
		
		card1 = new Label(yourHand[0]);
		card1.setMinWidth(Region.USE_PREF_SIZE);
		card2 = new Label(yourHand[1]);
		card2.setMinWidth(Region.USE_PREF_SIZE);
		card3 = new Label(yourHand[2]);
		card3.setMinWidth(Region.USE_PREF_SIZE);
		card4 = new Label(yourHand[3]);
		card4.setMinWidth(Region.USE_PREF_SIZE);
		card5 = new Label(yourHand[4]);
		card5.setMinWidth(Region.USE_PREF_SIZE);
		
		pane.add(youHaveLabel, 3, 0);
		pane.add(yourMoney, 1, 0);
		pane.add(card1, 1, 1);
		pane.add(card2, 1, 2);
		pane.add(card3, 1, 3);
		pane.add(card4, 1, 4);
		pane.add(card5, 1, 5);
		pane.add(betButton, 3, 1);
		pane.add(callButton, 3, 2);
		pane.add(checkButton, 3, 3);
		pane.add(raiseButton, 3, 4);
		pane.add(foldButton, 3, 5);
		
		potMoney = 0;
		potLabel = new Label("Pot:\n$" + potMoney);
		
		prompt = new Label("What would you like to do?");
		sp = new ScrollPane();
		sp.setPrefSize(70, 70);
		content = new VBox();
		sp.setContent(content);
		sp.setPannable(true);
		content.getChildren().add(prompt);
		
		textField = new TextField();
		textField.setVisible(false);
		textField.setMaxWidth(100);
		VBox middleScreen = new VBox(potLabel, sp, textField);
		middleScreen.setAlignment(Pos.CENTER);
		middleScreen.setSpacing(15);
		display.setCenter(middleScreen);
		
		player2 = new Label("Player 2:\n     ?\n     ?\n     ?\n     ?\n     ?");		
		player2Money = 1000;
		player2MoneyLabel = new Label("Money = $" + player2Money);
		VBox player2Box = new VBox(player2, player2MoneyLabel); 
		player2Box.setAlignment(Pos.CENTER);
		display.setLeft(player2Box);
		
		player3 = new Label("Player 3:\n?  ?  ?  ?  ?");		
		player3Money = 1000;
		player3MoneyLabel = new Label("Money = $" + player3Money);
		VBox player3Box = new VBox(player3, player3MoneyLabel); 
		player3Box.setAlignment(Pos.CENTER);
		display.setTop(player3Box);
		
		player4 = new Label("Player 4:\n     ?\n     ?\n     ?\n     ?\n     ?");		
		player4Money = 1000;
		player4MoneyLabel = new Label("Money = $" + player4Money);
		VBox player4Box = new VBox(player4, player4MoneyLabel); 
		player4Box.setAlignment(Pos.CENTER);
		display.setRight(player4Box);
		
		
		display.setStyle("-fx-font-weight: bold");
		scene1 = new Scene(display, 500, 500);
		primaryStage.setScene(scene1);
        primaryStage.show();
	}
	
	public static void endGame()
	{	
		formatHand(player2Hand);
		formatHand(player3Hand);
		formatHand(player4Hand);
		player2.setText("Player 2: "+player2Has+"\n"+player2Hand[0]+"\n"+player2Hand[1]+"\n"+player2Hand[2]+"\n"+player2Hand[3]+"\n"+player2Hand[4]);		
		player3.setText("Player 3: "+player3Has+"\n"+player3Hand[0]+"\n"+player3Hand[1]+"\n"+player3Hand[2]+"\n"+player3Hand[3]+"\n"+player3Hand[4]);		
		player4.setText("Player 4: "+player4Has+"\n"+player4Hand[0]+"\n"+player4Hand[1]+"\n"+player4Hand[2]+"\n"+player4Hand[3]+"\n"+player4Hand[4]);		
		
		String[] playersHave ={youHave, player2Has, player3Has, player4Has};
		int yourValue = 0, player2Value = 0, player3Value = 0, player4Value = 0; 
		int[] playerValue = {yourValue, player2Value, player3Value, player4Value};
		for (int i=0;i<playersHave.length;i++)
		{
			if (playersHave[i].matches("Royal Flush"))
				playerValue[i] = 10;
			else if (playersHave[i].matches("Straight Flush"))
				playerValue[i] = 9;
			else if (playersHave[i].matches("Four of a Kind"))
				playerValue[i] = 8;
			else if (playersHave[i].matches("Full House"))
				playerValue[i] = 7;
			else if (playersHave[i].matches("Flush"))
				playerValue[i] = 6;
			else if (playersHave[i].matches("Straight"))
				playerValue[i] = 5;
			else if (playersHave[i].matches("Three of a Kind"))
				playerValue[i] = 4;
			else if (playersHave[i].matches("Two Pairs"))
				playerValue[i] = 3;
			else if (playersHave[i].matches("Pair"))
				playerValue[i] = 2;
			else if (playersHave[i].matches("High Card"))
				playerValue[i] = 1;
		}
		Arrays.sort(playerValue);
		String thisText = "";
		if (playerValue[3] == playerValue[2])
		{
			if (playerValue[2] == playerValue[1])
			{
				if (playerValue[1] == playerValue[0])
				{
					thisText = "It's a four way tie! You all get your money back.";
				}
				else
					thisText = "Three players share the pot.";
			}
			else
				thisText = "Two players share the pot.";
		}
		else
			if (playerValue[3] == yourValue)
				thisText = "You win!";
			else if (playerValue[3] == player2Value)
				thisText = "Player 2 wins!";
			else if (playerValue[3] == player3Value)
				thisText = "Player 3 wins!";
			else if (playerValue[3] == player4Value)
				thisText = "Player 4 wins!";
		prompt.setText(prompt.getText() + "\n" + thisText);
		
	}
	
	public static void scrollToBottom()
	{
		//sp.setVvalue(1.0);
		sp.vvalueProperty().bind(content.heightProperty());
	}
	
	public static void pickCardEvent(Button cardButton, int cardNumber, ArrayList<Integer> cardList)
	{
		cardButton.setDisable(true);
		cardList.add(cardNumber);
	}
	
	public static void formatHand(String[] hand)
	{
		for (int i = 0;i < hand.length; i++)
		{
			String[] tempNum = hand[i].split(" ");
			if (tempNum[0].matches("14"))
				hand[i] = hand[i].replaceFirst("14", "Ace");
			else if (tempNum[0].matches("02"))
				hand[i] = hand[i].replaceFirst("02", "Two");
			else if (tempNum[0].matches("03"))
				hand[i] = hand[i].replaceFirst("03", "Three");
			else if (tempNum[0].matches("04"))
				hand[i] = hand[i].replaceFirst("04", "Four");
			else if (tempNum[0].matches("05"))
				hand[i] = hand[i].replaceFirst("05", "Five");
			else if (tempNum[0].matches("06"))
				hand[i] = hand[i].replaceFirst("06", "Six");
			else if (tempNum[0].matches("07"))
				hand[i] = hand[i].replaceFirst("07", "Seven");
			else if (tempNum[0].matches("08"))
				hand[i] = hand[i].replaceFirst("08", "Eight");
			else if (tempNum[0].matches("09"))
				hand[i] = hand[i].replaceFirst("09", "Nine");
			else if (tempNum[0].matches("10"))
				hand[i] = hand[i].replaceFirst("10", "Ten");
			else if (tempNum[0].matches("11"))
				hand[i] = hand[i].replaceFirst("11", "Jack");
			else if (tempNum[0].matches("12"))
				hand[i] = hand[i].replaceFirst("12", "Queen");
			else if (tempNum[0].matches("13"))
				hand[i] = hand[i].replaceFirst("13", "King");
		}
	}
	
	public static void unFormatHand(String[] hand)
	{
		for (int i = 0;i < hand.length; i++)
		{
			String[] tempNum = hand[i].split(" ");
			if (tempNum[0].matches("Ace"))
				hand[i] = hand[i].replaceFirst("Ace", "14");
			else if (tempNum[0].matches("Two"))
				hand[i] = hand[i].replaceFirst("Two", "02");
			else if (tempNum[0].matches("Three"))
				hand[i] = hand[i].replaceFirst("Three", "03");
			else if (tempNum[0].matches("Four"))
				hand[i] = hand[i].replaceFirst("Four", "04");
			else if (tempNum[0].matches("Five"))
				hand[i] = hand[i].replaceFirst("Five", "05");
			else if (tempNum[0].matches("Six"))
				hand[i] = hand[i].replaceFirst("Six", "06");
			else if (tempNum[0].matches("Seven"))
				hand[i] = hand[i].replaceFirst("Seven", "07");
			else if (tempNum[0].matches("Eight"))
				hand[i] = hand[i].replaceFirst("Eight", "08");
			else if (tempNum[0].matches("Nine"))
				hand[i] = hand[i].replaceFirst("Nine", "09");
			else if (tempNum[0].matches("Ten"))
				hand[i] = hand[i].replaceFirst("Ten", "10");
			else if (tempNum[0].matches("Jack"))
				hand[i] = hand[i].replaceFirst("Jack", "11");
			else if (tempNum[0].matches("Queen"))
				hand[i] = hand[i].replaceFirst("Queen", "12");
			else if (tempNum[0].matches("King"))
				hand[i] = hand[i].replaceFirst("King", "13");
		}
	}
	
	public static void cardSwap()
	{
		swappedCards = true;
		if (player2Fold == false)
		{			
			prompt.setText(prompt.getText() + "\nPlayer 2 swapped " + player2Remove.length + " card(s)");
			for (int i=0;i<player2Remove.length;i++)
			{
				for (int j=0;j<5;j++)
				{
					if (player2Remove[i].matches(player2Hand[j]))
					{
						player2Hand[j] = deck.get(0);
						deck.remove(0);
					}
				}
			}
			String[] player2Array = checkHand(player2Hand);
			player2Has = player2Array[player2Array.length-1];
		}
		if (player3Fold == false)
		{
			prompt.setText(prompt.getText() + "\nPlayer 3 swapped " + player3Remove.length + " card(s)");
			for (int i=0;i<player3Remove.length;i++)
			{
				for (int j=0;j<5;j++)
				{
					if (player3Remove[i].matches(player3Hand[j]))
					{
						player3Hand[j] = deck.get(0);
						deck.remove(0);
					}
				}
			}
			String[] player3Array = checkHand(player3Hand);
			player3Has = player3Array[player3Array.length-1];
		}
		if (player4Fold == false)
		{
			prompt.setText(prompt.getText() + "\nPlayer 4 swapped " + player4Remove.length + " card(s)");
			for (int i=0;i<player4Remove.length;i++)
			{
				for (int j=0;j<5;j++)
				{
					if (player4Remove[i].matches(player4Hand[j]))
					{
						player4Hand[j] = deck.get(0);
						deck.remove(0);
					}
				}
			}
			String[] player4Array = checkHand(player4Hand);
			player4Has = player4Array[player4Array.length-1];
		}
		whosTurn = 1;
		checkButton.setDisable(false);
		betButton.setDisable(false);
		foldButton.setDisable(false);
	}
	
	public static void finishedCardPick(ArrayList<Integer> cardList, Button button1, Button button2, Button button3, Button button4, Button button5, Button button6)
	{
		pane.getChildren().remove(button1);
		pane.getChildren().remove(button2);
		pane.getChildren().remove(button3);
		pane.getChildren().remove(button4);
		pane.getChildren().remove(button5);
		pane.getChildren().remove(button6);
		prompt.setText(prompt.getText() + "\nYou swapped " + cardList.size() + " card(s)");
		for (int i = 0; i < cardList.size(); i++)
		{
			//yourHand[cardList[i]] = deck.get(0);
			//deck.remove(0);
			if (cardList.get(i) == 1)
			{
				yourHand[0] = deck.get(0);
				//formatHand(yourHand);
				//card1.setText(yourHand[0]);
			}
			else if (cardList.get(i) == 2)
			{
				yourHand[1] = deck.get(0);
				//formatHand(yourHand);
				//card2.setText(yourHand[1]);
			}
			else if (cardList.get(i) == 3)
			{
				yourHand[2] = deck.get(0);
				//formatHand();
				//card3.setText(yourHand[2]);
			}
			else if (cardList.get(i) == 4)
			{
				yourHand[3] = deck.get(0);
				//formatHand();
				//card4.setText(yourHand[3]);
			}
			else if (cardList.get(i) == 5)
			{
				yourHand[4] = deck.get(0);
				//formatHand();
				//card5.setText(yourHand[4]);
			}
			//formatHand();
			deck.remove(0);
		}
		unFormatHand(yourHand);
		String[] yourArray = checkHand(yourHand);
		//String newYouHave = yourArray[yourArray.length-1];
		youHave = yourArray[yourArray.length-1];
		youHaveLabel.setText(youHave);
		formatHand(yourHand);
		card1.setText(yourHand[0]);
		card2.setText(yourHand[1]);
		card3.setText(yourHand[2]);
		card4.setText(yourHand[3]);
		card5.setText(yourHand[4]);
		cardSwap();
	}
	
	public static void deal()
	{
		//System.out.println("DEAL");
		Button doneButton = new Button("Done");
		Button pickCard1 = new Button();
		Button pickCard2 = new Button();
		Button pickCard3 = new Button();
		Button pickCard4 = new Button();
		Button pickCard5 = new Button();
		
		ArrayList<Integer> arrlist = new ArrayList<Integer>(5);
		
		pickCard1.setOnAction(e -> pickCardEvent(pickCard1, 1, arrlist));
		pickCard2.setOnAction(e -> pickCardEvent(pickCard2, 2, arrlist));
		pickCard3.setOnAction(e -> pickCardEvent(pickCard3, 3, arrlist));
		pickCard4.setOnAction(e -> pickCardEvent(pickCard4, 4, arrlist));
		pickCard5.setOnAction(e -> pickCardEvent(pickCard5, 5, arrlist));
		doneButton.setOnAction(e -> finishedCardPick(arrlist, doneButton, pickCard1, pickCard2, pickCard3, pickCard4, pickCard5));
		
		pane.add(doneButton, 0, 0);
		pane.add(pickCard1, 0, 1);
		pane.add(pickCard2, 0, 2);
		pane.add(pickCard3, 0, 3);
		pane.add(pickCard4, 0, 4);
		pane.add(pickCard5, 0, 5);
		
		prompt.setText(prompt.getText() + "\nDealer will now allow you to swap cards.");
		prompt.setText(prompt.getText() + "\nPick which cards you'd like to replace.");
	}
	
	public static void checkForDeal()
	{
		if (loopedOnce == true && yourDebt == 0 && player2Debt == 0 && player3Debt == 0 && player4Debt == 0)
			if (swappedCards)
				endGame();
			else
				deal();
		else
			if (whosTurn == 2)
			{
				whosTurn = 3;
				opponentGoes();
			}
			else if (whosTurn == 3)
			{
				whosTurn = 4;
				loopedOnce = true;
				opponentGoes();
			}
			else if (whosTurn == 4)
			{
				whosTurn = 1;
				prompt.setText(prompt.getText() + "\nIt's $" + yourDebt + " to you.");
				callButton.setDisable(false);
				raiseButton.setDisable(false);
				foldButton.setDisable(false);
			}
	}
	
	public static void opponentBets()
	{
		String playerHas = "";
		int playerMoney = 0;
		Label playerLabel = new Label();
		int playerDebt = 0;
		boolean playerFolds = false;
		if (whosTurn == 2)
		{
			playerHas = player2Has;
			playerMoney = player2Money;
			playerLabel = player2MoneyLabel;
			playerDebt = player2Debt;
			playerFolds = player2Fold;
		}
		else if (whosTurn == 3)
		{
			playerHas = player3Has;
			playerMoney = player3Money;
			playerLabel = player3MoneyLabel;
			playerDebt = player3Debt;
			playerFolds = player3Fold;
		}
		else if (whosTurn == 4)
		{
			playerHas = player4Has;
			playerMoney = player4Money;
			playerLabel = player4MoneyLabel;
			playerDebt = player4Debt;
			playerFolds = player4Fold;
		}
		
		String whatBet = "low";
		int random = (int)(Math.random() * 50 + 1);
		if (playerHas.matches("Royal Flush"))
			if (random > 34 && random < 46)
				whatBet = "medium";
			else if (random > 0 && random < 35)
				whatBet = "high";
		else if (playerHas.matches("Straight Flush"))
			if (random > 29 && random < 46)
				whatBet = "medium";
			else if (random > 0 && random < 30)
				whatBet = "high";
		else if (playerHas.matches("Four of a Kind"))
			if (random > 24 && random < 46)
				whatBet = "medium";
			else if (random > 0 && random < 25)
				whatBet = "high";
		else if (playerHas.matches("Full House"))
			if (random > 34 && random < 41)
				whatBet = "high";
			else if (random > 0 && random < 35)
				whatBet = "medium";
		else if (playerHas.matches("Flush"))
			if (random > 36 && random < 41)
				whatBet = "high";
			else if (random > 0 && random < 37)
				whatBet = "medium";
		else if (playerHas.matches("Straight"))
			if (random > 36 && random < 41)
				whatBet = "high";
			else if (random > 0 && random < 37)
				whatBet = "medium";
		else if (playerHas.matches("Three of a Kind"))
			if (random > 38 && random < 41)
				whatBet = "high";
			else if (random > 0 && random < 39)
				whatBet = "medium";
		else if (playerHas.matches("Two Pairs"))
			if (random > 39 && random < 46)
				whatBet = "high";
			else if (random > 45 && random < 51)
				whatBet = "medium";
		else if (playerHas.matches("Pair"))
			if (random > 39 && random < 43)
				whatBet = "high";
			else if (random > 42 && random < 51)
				whatBet = "medium";
		else
			if (random > 39 && random < 41)
				whatBet = "high";
			else if (random > 40 && random < 51)
				whatBet = "medium";
			
		boolean shouldFold = false;
		boolean shouldCheck = false;
		boolean shouldCall = false;
		boolean shouldBet = false;
		String decision = "\nPlayer raises $";
		if (lastBetFigure == 0)
			decision = "\nPlayer bets $";
		random = (int)(Math.random() * 10 + 1);
		if (lastBet.matches("none") && random < 8)
			shouldCheck = true;
		else if ((whatBet.matches("low") && lastBet.matches("high")) || (whatBet.matches("low") && lastBet.matches("medium")) || (whatBet.matches("medium") && lastBet.matches("high")))
			shouldFold = true;
		else if (random < 8 && (whatBet.matches("low") && lastBet.matches("low")) || (whatBet.matches("medium") && lastBet.matches("medium")) || (whatBet.matches("high") && lastBet.matches("high")))
			shouldCall = true;
		
		if (lastBet.matches("very high"))
		{
			prompt.setText(prompt.getText() + "\nPlayer Folds.");
			playerFolds = true;
		}
		else if (shouldCheck == true)
		{
			prompt.setText(prompt.getText() + "\nPlayer Checks.");
		}
		else if (shouldCall == true)
		{
			prompt.setText(prompt.getText() + "\nPlayer Calls. (" + playerDebt + ")");
			playerMoney = playerMoney - playerDebt;
			playerDebt = 0;
			playerLabel.setText("Money = $" + playerMoney);
			potMoney = potMoney + lastBetFigure;
			potLabel.setText("Pot:\n$" + potMoney);
		}
		else if (shouldFold == true && random < 6)
		{
			prompt.setText(prompt.getText() + "\nPlayer Folds.");
			playerFolds = true;
		}
		else if (shouldFold == false && whatBet == "low" && random < 3)
		{
			prompt.setText(prompt.getText() + "\nPlayer Folds.");
			playerFolds = true;
		}
		else
		{
			if (whatBet.matches("high"))
			{
				shouldBet = true;
				random = (int)(Math.random() * 100 + 1);
				random += 100;
				random = (random + 4) / 5 * 5;
			}
			else if (whatBet.matches("medium"))
			{
				shouldBet = true;
				random = (int)(Math.random() * 50 + 1);
				random += 50;
				random = (random + 4) / 5 * 5;
			}
			else
			{	
				shouldBet = true;
				random = (int)(Math.random() * 50 + 1);
				random = (random + 4) / 5 * 5;
			}	
		}
		
		if (shouldBet)
		{
			if (playerMoney >= random)
			{
				prompt.setText(prompt.getText() + decision + random + ".");
				playerMoney = playerMoney - random - lastBetFigure;
				lastBetFigure = lastBetFigure + random;
				potMoney = potMoney + lastBetFigure + random;
				potLabel.setText("Pot:\n$" + potMoney);
				playerLabel.setText("Money = $" + playerMoney);
				playerDebt = 0;
				yourDebt += random;
				if (whosTurn == 2)
				{
					player3Debt += random;
					player4Debt += random;
				}
				else if (whosTurn == 3)
				{
					player2Debt += random;
					player4Debt += random;
				}
				else if (whosTurn == 4)
				{
					player2Debt += random;
					player3Debt += random;
				}
				if (lastBetFigure > 0 && lastBetFigure < 50)
					lastBet = "low";
				else if (lastBetFigure > 49 && lastBetFigure < 100)
					lastBet = "medium";
				else if (lastBetFigure > 99 && lastBetFigure < 200)
					lastBet = "high";
			}
			else
			{
				prompt.setText(prompt.getText() + "\nPlayer Folds.");
				playerFolds = true;
			}
		}
		
		if (whosTurn == 2)
		{
			player2Money = playerMoney;
			player2Debt = playerDebt;
			player2Fold = playerFolds;
		}
		else if (whosTurn == 3)
		{
			player3Money = playerMoney;
			player3Debt = playerDebt;
			player3Fold = playerFolds;
		}
		else if (whosTurn == 4)
		{
			player4Money = playerMoney;
			player4Debt = playerDebt;
			player4Fold = playerFolds;
		}
		scrollToBottom();
		checkForDeal();
	}
	
	public static void opponentGoes()
	{	
		if (whosTurn == 2 && player2Fold == true)
			whosTurn = 3;
		
		if (whosTurn == 3 && player3Fold == true)
			whosTurn = 4;
		
		if (whosTurn == 4 && player4Fold == true)
		{
			whosTurn = 1;
			callButton.setDisable(false);
			raiseButton.setDisable(false);
			foldButton.setDisable(false);
		}
		
		if (whosTurn == 2)
		{
			prompt.setText(prompt.getText() + "\nPlayer 2's turn.");
			opponentBets();
		}
		else if (whosTurn == 3)
		{
			prompt.setText(prompt.getText() + "\nPlayer 3's turn.");
			opponentBets();
		}
		else if (whosTurn == 4)
		{
			prompt.setText(prompt.getText() + "\nPlayer 4's turn.");
			opponentBets();
		}
	}
	
	public static void betEvent(String whichOne)
	{
		if (betting == false)
		{
			betting = true;
			textField.setVisible(true);
			prompt.setText(prompt.getText() + "\nHow much would you like to " + whichOne + "?");
			if (whichOne.matches("bet"))
				raiseButton.setDisable(true);
			else
				betButton.setDisable(true);
			callButton.setDisable(true);
			checkButton.setDisable(true);
			foldButton.setDisable(true);
		}
		else
		{
			betting = false;
			String yourBet = textField.getText();
			int yourBetInt = Integer.parseInt(yourBet);
			money = money - yourBetInt;
			textField.setVisible(false);
			potMoney = potMoney + yourBetInt;
			potLabel.setText("Pot:\n$" + potMoney);
			yourMoney.setText("Money = $" + money);
			String yourDecision = "\nYou raise $";
			if (whichOne.matches("bet"))
				yourDecision = "\nYou bet $";
			prompt.setText(prompt.getText() + yourDecision + yourBetInt);
			if (yourBetInt > 0 && yourBetInt < 50)
				lastBet = "low";
			else if (yourBetInt > 49 && yourBetInt < 100)
				lastBet = "medium";
			else if (yourBetInt > 99 && yourBetInt < 200)
				lastBet = "high";
			else
				lastBet = "very high";
			lastBetFigure = yourBetInt;
			player2Debt += yourBetInt;
			player3Debt += yourBetInt;
			player4Debt += yourBetInt;
			whosTurn = 2;
			opponentGoes();
		}
	}
	
	public static void checkEvent()
	{
		prompt.setText(prompt.getText() + "\nYou check.");
		betButton.setDisable(true);
		checkButton.setDisable(true);
		callButton.setDisable(true);
		raiseButton.setDisable(true);
		foldButton.setDisable(true);
		whosTurn = 2;
		opponentGoes();
	}
	
	public static void callEvent()
	{
		prompt.setText(prompt.getText() + "\nYou call.");
		betButton.setDisable(true);
		checkButton.setDisable(true);
		callButton.setDisable(true);
		raiseButton.setDisable(true);
		foldButton.setDisable(true);
		betting = false;
		money = money - yourDebt;
		potMoney = potMoney + yourDebt;
		potLabel.setText("Pot:\n$" + potMoney);
		yourMoney.setText("Money = $" + money);
		yourDebt = 0;
		whosTurn = 2;
		opponentGoes();
	}
	
	public static ArrayList<String> makeDeck()
	{
		String[] SUITS = 
		{
            "Clubs", "Diamonds", "Hearts", "Spades"
        };
        String[] RANKS = 
		{
            "02", "03", "04", "05", "06", "07", "08", "09", "10",
            "11", "12", "13", "14"
        };
        // initialize deck
        int n = SUITS.length * RANKS.length;
        //String[] deck = new String[n];
		ArrayList<String> deck = new ArrayList<>();
        for (int i = 0; i < RANKS.length; i++) 
		{
            for (int j = 0; j < SUITS.length; j++) 
			{
                //deck[SUITS.length*i + j] = RANKS[i] + " of " + SUITS[j];
				deck.add(RANKS[i] + " of " + SUITS[j]);
            }
        }
        // shuffle
		/*
        for (int i = 0; i < n; i++) 
		{
            int r = i + (int) (Math.random() * (n-i));
            String temp = deck[r];
            deck[r] = deck[i];
            deck[i] = temp;
        }
		*/
		Collections.shuffle(deck);
		
		//String[] yourHand = {deck.get(0), deck.get(1), deck.get(2), deck.get(3), deck.get(4)};
		//String[] yourHand = new String[5];
		//for (int i = 0;i < 5; i++)
		//{
		//	yourHand[i] = deck.get(0);
		//	deck.remove(0);
		//}
		//String yourHand = "Your Hand:\n" + deck[0] + " " + deck[1] + " " + deck[2] + " " + deck[3] + " " + deck[4] + "\n";
		//System.out.println(yourHand);

		
		//return yourHand; //+ "\n" + youHave;
		return deck;
	}
	
	public static String[] checkHand(String[] hand)
	{
		//String[] hand = {"04 of Spades", "05 of Clubs", "06 of Diamonds", "07 of Hearts", "09 of Clubs"};
		int clubs=0, hearts=0, spades=0, diamonds=0;
		ArrayList<String> numbers = new ArrayList<String>();
		int[] intArray = new int[5];
		int lastNum = -1;
		ArrayList<String> cardsToKeep = new ArrayList<String>();
		for (int i = 0; i < 5; i++) 
		{
			intArray[i] = Integer.parseInt(hand[i].substring(0, 2));
			int howMany = 0;
			if (hand[i].matches(".*\\bClubs\\b.*"))
				clubs++;
			else if (hand[i].matches(".*\\bHearts\\b.*"))
				hearts++;
			else if (hand[i].matches(".*\\bSpades\\b.*"))
				spades++;
			else if (hand[i].matches(".*\\bDiamonds\\b.*"))
				diamonds++;
			
			for (int j = 0; j < 5; j++)
			{
				int check1 = Integer.parseInt(hand[i].substring(0, 2));
				int check2 = Integer.parseInt(hand[j].substring(0, 2));
				if (check1 == check2 && i != j && i != lastNum)
				{
					howMany++;
					lastNum = j;
					cardsToKeep.add(hand[i]);
					cardsToKeep.add(hand[j]);
				}
			}
			if (howMany > 0)
				numbers.add(Integer.toString(howMany));
		}
		boolean pair = false, twoPair = false, three = false, four = false, fullHouse = false, flush = false, straight = false, royalFlush = false;
		int hereAgain = 0;
		for (int i = 0; i < numbers.size(); i++)
		{
			if (numbers.get(i).matches("1"))
			{
				pair = true;
				hereAgain++;
			}
			else if (numbers.get(i).matches("2"))
				three = true;	
			else if (numbers.get(i).matches("3"))
				four = true;
		}
		
		ArrayList<String> cardsToRemove = new ArrayList<String>();
		for (int i = 0;i < 5;i++)
		{
			boolean temp = false;
			for (int j = 0;j < cardsToKeep.size();j++)
			{
				if (hand[i].matches(cardsToKeep.get(j)))
					temp = true;
			}
			if (temp == false)
				cardsToRemove.add(hand[i]);
		}
		
		/*
		System.out.println("\nCARDS TO REMOVE");
		for (int i = 0;i < cardsToRemove.size();i++)
		{
			System.out.println(cardsToRemove.get(i));
		}
		System.out.println("END\n");
		*/
		
		if (hereAgain > 1)
			twoPair = true;
		
		if (diamonds == 5 || hearts == 5 || clubs == 5 || spades == 5)
			flush = true;
		
		ArrayList<String> tempCardsToRemove = new ArrayList<String>();
		//if (diamonds == 4 || hearts == 4 || clubs == 4 || spades == 4 || diamonds == 3 || hearts == 3 || clubs == 3 || spades == 3)
		if (three == false && four == false)
		{
			if (diamonds == 4 || diamonds == 3)
			{
				for (int i = 0;i < 5;i++)
				{
					if (!(hand[i].matches(".*\\bDiamonds\\b.*")))
					{
						tempCardsToRemove.add(hand[i]);
					}
				}
			}
			else if (hearts == 4 || hearts == 3)
			{
				for (int i = 0;i < 5;i++)
				{
					if (hand[i].matches(".*\\bHearts\\b.*"))
					{
						tempCardsToRemove.add(hand[i]);
					}
				}
			}
			else if (clubs == 4 || clubs == 3)	
			{
				for (int i = 0;i < 5;i++)
				{
					if (!(hand[i].matches(".*\\bClubs\\b.*")))
					{
						tempCardsToRemove.add(hand[i]);
					}
				}
			}
			else if (spades == 4 || spades == 3)
			{
				for (int i = 0;i < 5;i++)
				{
					if (!(hand[i].matches(".*\\bSpades\\b.*")))
					{
						tempCardsToRemove.add(hand[i]);
					}
				}
			}
		}
		
		if (tempCardsToRemove.size() > 0)
		{
			cardsToRemove.clear();
			for (int i = 0;i < tempCardsToRemove.size();i++)
			{
				cardsToRemove.add(tempCardsToRemove.get(i));
			}
		}
		
		/*
		System.out.println("\nCARDS TO REMOVE");
		for (int i = 0;i < cardsToRemove.size();i++)
		{
			System.out.println(cardsToRemove.get(i));
		}
		System.out.println("END\n");
		*/
		
		Arrays.sort(intArray);
		int straightNum = 0;
		ArrayList<String> tempCardsToRemove2 = new ArrayList<String>();
		for (int i = 1; i < intArray.length; i++)
		{ 
			if (intArray[i]-1 == intArray[i-1])
			{
				straightNum++;
			}
			else
			{
				//System.out.println("length = " + intArray.length + " and i = " + i);
				if (intArray[i]-2 != intArray[i-1])
				{
					if (intArray[i-1] > 9)
						tempCardsToRemove2.add("" + intArray[i-1]);
					else
						tempCardsToRemove2.add("0" + intArray[i-1]);
				}
				else if (intArray[i]-2 == intArray[i-1] && i == 1)
				{
					if (intArray[i-1] > 9)
						tempCardsToRemove2.add("" + intArray[i-1]);
					else
						tempCardsToRemove2.add("0" + intArray[i-1]);
				}
				else if (intArray[i]-2 == intArray[i-1] && i == intArray.length-1)
				{
					if (intArray[i] > 9)
						tempCardsToRemove2.add("" + intArray[i]);
					else
						tempCardsToRemove2.add("0" + intArray[i]);
				}
			}
		}
		//for (int i = 0;i < tempCardsToRemove2.size();i++)
		//{
		//	System.out.println(tempCardsToRemove2.get(i));
		//}
		
		if (intArray[0] == 10)
			royalFlush = true;
		
		if (straightNum == 4)
			straight = true;
		
		if (straightNum == 2 || straightNum == 3)
		{
			if (three == false && four == false && flush == false && royalFlush == false && tempCardsToRemove.size() == 0)
			{
				if (tempCardsToRemove2.size() > 0)
				{
					cardsToRemove.clear();
					for (int i = 0;i < tempCardsToRemove2.size();i++)
					{
						for (int j = 0;j < 5;j++)
						{
							//System.out.println(tempCardsToRemove2.get(i).equals(hand[j].substring(0, 2)));
							if (tempCardsToRemove2.get(i).matches(hand[j].substring(0, 2)))
							{
								//System.out.println(tempCardsToRemove2.get(i).equals(hand[j].substring(0, 2)));
								//System.out.println(tempCardsToRemove2.get(i) + " and " + hand[j].substring(0, 2));
								cardsToRemove.add(hand[j]);
							}
						}
					}
				}
			}
		}
		
		String youHave = "";
		if (royalFlush == true && flush == true)
		{
			youHave = "Royal Flush";
			cardsToRemove.clear();
		}
		else if (flush == true && straight == true)
		{
			youHave = "Straight Flush";
			cardsToRemove.clear();
		}
		else if (four == true)
		{
			youHave = "Four of a Kind";
		}
		else if (pair == true && three == true)
		{
			youHave = "Full House";
			cardsToRemove.clear();
		}
		else if (flush == true)
		{
			youHave = "Flush";
			cardsToRemove.clear();
		}
		else if (straight == true)
		{
			youHave = "Straight";
			cardsToRemove.clear();
		}
		else if (three == true)
		{
			youHave = "Three of a Kind";
		}
		else if (twoPair == true)
		{
			youHave = "Two Pairs";
		}
		else if (pair == true)
		{
			youHave = "Pair";
		}
		else
		{
			youHave = "High Card";
		}
		
		/*
		System.out.println("\nCARDS TO REMOVE");
		for (int i = 0;i < cardsToRemove.size();i++)
		{
			System.out.println(cardsToRemove.get(i));
		}
		System.out.println("END\n");
		*/
		
		int index = cardsToRemove.size();
		String[] ar = new String[index+1];
		for (int i = 0;i < index;i++)
		{
			ar[i] = cardsToRemove.get(i);
		}
		ar[index] = youHave;
		return ar;
		//String[][] returnThese = {youHave, ar};
		//return returnThese;
		//return youHave;
	}
	
	public static String[] hand()
	{
		String[] yourHand = new String[5];
		for (int i = 0;i < 5; i++)
		{
			yourHand[i] = deck.get(0);
			deck.remove(0);
		}
		
		/*
		for (int i = 0;i < yourHand.length; i++)
		{
			String[] tempNum = yourHand[i].split(" ");
			if (tempNum[0].matches("14"))
				yourHand[i] = yourHand[i].replaceFirst("14", "Ace");
			else if (tempNum[0].matches("02"))
				yourHand[i] = yourHand[i].replaceFirst("02", "Two");
			else if (tempNum[0].matches("03"))
				yourHand[i] = yourHand[i].replaceFirst("03", "Three");
			else if (tempNum[0].matches("04"))
				yourHand[i] = yourHand[i].replaceFirst("04", "Four");
			else if (tempNum[0].matches("05"))
				yourHand[i] = yourHand[i].replaceFirst("05", "Five");
			else if (tempNum[0].matches("06"))
				yourHand[i] = yourHand[i].replaceFirst("06", "Six");
			else if (tempNum[0].matches("07"))
				yourHand[i] = yourHand[i].replaceFirst("07", "Seven");
			else if (tempNum[0].matches("08"))
				yourHand[i] = yourHand[i].replaceFirst("08", "Eight");
			else if (tempNum[0].matches("09"))
				yourHand[i] = yourHand[i].replaceFirst("09", "Nine");
			else if (tempNum[0].matches("10"))
				yourHand[i] = yourHand[i].replaceFirst("10", "Ten");
			else if (tempNum[0].matches("11"))
				yourHand[i] = yourHand[i].replaceFirst("11", "Jack");
			else if (tempNum[0].matches("12"))
				yourHand[i] = yourHand[i].replaceFirst("12", "Queen");
			else if (tempNum[0].matches("13"))
				yourHand[i] = yourHand[i].replaceFirst("13", "King");
		}
		*/
		
		return yourHand;
	}
	
	public static void main(String[] args)
	{
        launch(args);
    }
}

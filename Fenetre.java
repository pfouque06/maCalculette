package maCalculette;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashSet;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import javaTools.Logger;
import javaTools.Observer;

public class Fenetre extends JFrame implements ActionListener, KeyListener {

	// logger
	Logger logger = Main.logger;

	// locales
	private JPanel container = new JPanel();
	private JPanel labelPan = new JPanel();
	private JLabel label = new JLabel("0");
	private JPanel keyboardPan = new JPanel();

	private String[] kNames = { "7", "8", "9", "4", "5", "6", "1", "2", "3", "0", ".", "<" };
	private ArrayList<String> kLabels = new ArrayList<String>(Arrays.asList(kNames));
	private JButton[] kButtons = new JButton[12];
	// private int[] kKeys = { KeyEvent.VK_7, KeyEvent.VK_8, KeyEvent.VK_9,
	// KeyEvent.VK_4, KeyEvent.VK_5, KeyEvent.VK_6, KeyEvent.VK_1, KeyEvent.VK_2,
	// KeyEvent.VK_3, KeyEvent.VK_0, KeyEvent.VK_PERIOD, KeyEvent.VK_BACK_SPACE} ;
	// private int[] kKeys = { 16777448, KeyEvent.VK_UNDERSCORE, 16777415,
	// KeyEvent.VK_QUOTE, KeyEvent.VK_LEFT_PARENTHESIS, KeyEvent.VK_MINUS,
	// KeyEvent.VK_AMPERSAND, 16777449, KeyEvent.VK_QUOTEDBL, 16777440,
	// KeyEvent.VK_SEMICOLON, KeyEvent.VK_BACK_SPACE} ;

	private JPanel ActionPan = new JPanel();
	private String[] aNames = { "/", "C", "x", "MC", "-", "MS", "+", "=" };
	private ArrayList<String> aLabels = new ArrayList<String>(Arrays.asList(aNames));
	private JButton[] aButtons = new JButton[8];
	// private int[] aKeys = { KeyEvent.VK_DOWN, 127, KeyEvent.VK_UP ,
	// KeyEvent.VK_PAGE_UP, KeyEvent.VK_LEFT, KeyEvent.VK_PAGE_DOWN,
	// KeyEvent.VK_RIGHT, 10};
	// private int[] aKeys = { -1, 127, -1, -1, -1, -1, -1, 10};

	int indexMS = 0;

	private Calculette calculette;

	void processKeytoButtonBinding(String keyPressed) {
		logger.logging(">> keybindAction.processKeytoButtonBinding(" + keyPressed + ")");

		// check against Digit Pan Buttons
		if (kLabels.contains(keyPressed))
			kButtons[kLabels.indexOf(keyPressed)].doClick();

		// check against action Pan Buttons
		if (aLabels.contains(keyPressed))
			aButtons[aLabels.indexOf(keyPressed)].doClick();
	}

	public Fenetre() {
		// TODO Auto-generated constructor stub

		// On initialise la JFrame
		this.setTitle("Calculette");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(300, 220);
		// this.setSize(290, 210); // Home setting
		this.setLocationRelativeTo(null);
		// this.setResizable(true);
		this.setResizable(false);
		this.setAlwaysOnTop(true);

		// init Digit Keyboard Buttons
		keyboardPan.setPreferredSize(new Dimension(165, 225));
		Dimension dim = new Dimension(50, 30);
		Font kFont = new Font("DS-digital", Font.TYPE1_FONT, 15);
		// Font kFont = new Font("Goudy Bookletter 1911", Font.TYPE1_FONT, 15);
		// Goudy Bookletter 1911
		for (int indexCell = 0; indexCell < kNames.length; indexCell++) {
			kButtons[indexCell] = new JButton(kNames[indexCell]);
			kButtons[indexCell].setPreferredSize(dim);
			kButtons[indexCell].addActionListener(this);
			kButtons[indexCell].setFont(kFont);
			keyboardPan.add(kButtons[indexCell]);
		}

		ActionPan.setPreferredSize(new Dimension(125, 225));
		Dimension aDim = new Dimension(55, 30);
		// Font aFont = new Font("Dialog", Font.BOLD, 12); //PLAIN , ITALIC,
		for (int indexCell = 0; indexCell < aNames.length; indexCell++) {
			aButtons[indexCell] = new JButton(aNames[indexCell]);
			aButtons[indexCell].setPreferredSize(aDim);
			aButtons[indexCell].addActionListener(this);
			// aButtons[indexCell].setFont(aFont);
			ActionPan.add(aButtons[indexCell]);
		}
		//set index of MS button
		if (aLabels.contains("MS"))
			indexMS = aLabels.indexOf("MS");

		// On initialise le JLabel
		// Font dispo :
		// Font police = new Font("DS-digital", Font.TYPE1_FONT, 20);
		// Font police = new Font("Segment14", Font.PLAIN, 25);
		Font police = null;
		File fileFont;
		try {
			// BufferedWriter writer;
			String workingDir = new File("").getAbsolutePath();
			logger.logging("workingDir: " + workingDir);
			// URL resource = Fenetre.class.getResource("/");
			// logger.logging("resource.getPath(): "+resource.getPath());
			String resourceDir = this.getClass().getResource("/").getPath();
			String classPath = resourceDir.substring(0, resourceDir.lastIndexOf("/bin"));
			logger.logging("classPath: " + classPath);

			// fileFont = new File("font/ledfont-sharp-Regular.otf");
			// fileFont = new File("font/PixelOperator-Bold.ttf");
			// fileFont = new File("font/PixelOperatorHB.ttf");
			// fileFont = new File("font/PixelOperatorHBSC.ttf");
			// fileFont = new File("font/PixelOperator.ttf");
			fileFont = new File(classPath + "/" + "font/PixelOperator.ttf");
			logger.logging("fileFont: " + fileFont.getAbsolutePath());
			police = Font.createFont(Font.TRUETYPE_FONT, fileFont);
			// police = police.deriveFont((float)20);
			// police = police.deriveFont(Font.BOLD, (float)25);
			police = police.deriveFont(Font.PLAIN, (float) 35);
		} catch (Exception ex) {
			System.err.println(ex.getMessage());
		}

		label.setFont(police);
		// label.setBorder(new LineBorder(Color.black, 1));
		label.setBorder(new EmptyBorder(4, 4, 4, 4));
		label.setHorizontalAlignment(JLabel.RIGHT);
		// label.setBackground(Color.white);
		labelPan.setLayout(new BorderLayout());
		labelPan.setBorder(new LineBorder(Color.darkGray, 1));
		// labelPan.setBorder(new EmptyBorder(2, 2, 2, 2));
		labelPan.setBackground(Color.white);
		labelPan.add(label);

		// On initialise le container panel de la JFrame
		container.setLayout(new BorderLayout());
		// container.setBorder(new EmptyBorder(2, 2, 2, 2));
		container.add(labelPan, BorderLayout.NORTH);
		container.add(keyboardPan, BorderLayout.WEST);
		container.add(ActionPan, BorderLayout.EAST);
		this.setContentPane(container);
		this.setVisible(true);
		this.setAlwaysOnTop(true);
		this.getRootPane().setDefaultButton(aButtons[7]); // CR is "=" as default button
		this.addKeyListener(this);
		this.setFocusable(true);
		this.requestFocus();

        // Add window listener by implementing WindowAdapter class to the frame instance.
		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				logger.logging(">> Fenetre.addWindowListener(WindowAdapter.windowClosing(" + e.getID() + "))");
				// System.exit(0);
			}
		});

		// On initialise la calculette
		this.calculette = new Calculette();
		// On place un écouteur sur la calculettte
		this.calculette.addObserver(new Observer() {

			@Override
			public void update(LinkedHashSet<String[]> pLHS) {
				// TODO Auto-generated method stub
				logger.logging(" >> update(pLHS)");
				
				// updating provided LHS
				for (String[] item : pLHS) {
					logger.logging(" >> [" + item[0] + ":" + item[1] + "]");
					switch (item[0]) {
					case "label" :
						label.setText( item[1].isEmpty() ? "0" : item[1] );
						break;
					case "MSet" :
						aButtons[indexMS].setForeground((item[1].equals("true") ? Color.blue : Color.black));
						break;
					}
				}
			}
			// redraw container ....
			//container.setVisible(false);
			//container.setVisible(true);
		});

		// run Horloge
		this.calculette.run();
	}

	@Override
	public void keyReleased(KeyEvent ke) {
	}

	@Override
	public void keyTyped(KeyEvent ke) {
	}

	@Override
	public void keyPressed(KeyEvent ke) {
		// logger.logging("Key pressed code=" + ke.getKeyCode() + ", extended code="
		// + ke.getExtendedKeyCode() +
		// ", char=" + ke.getKeyChar() + ", text=" +
		// KeyEvent.getKeyText(ke.getKeyCode()));
		// logger.logging("\tke=" + ke);
		int keyCode = ke.getKeyCode();
		char keyChar = ke.getKeyChar();
		String keyText = KeyEvent.getKeyText(ke.getKeyCode());
		String keyPressed = "";
		switch (keyChar) {
		// KeyboardPan
		case '.': // case .
		case ',': // case .
		case ';': // case .
		case ':': // case .
			keyPressed = ".";
			break;
		case '0':
		case 'à': // case à = 0
			keyPressed = "0";
			break;
		case '1':
		case '&': // case 1
			keyPressed = "1";
			break;
		case '2':
		case 'é': // case é = 2
			keyPressed = "2";
			break;
		case '3':
		case '\"': // case 3
			keyPressed = "3";
			break;
		case '4':
		case '\'': // case 4
			keyPressed = "4";
			break;
		case '5':
		case '(': // case 5
			keyPressed = "5";
			break;
		case '6': // case 6
			keyPressed = "6";
			break;
		case '7':
		case 'è': // case è = 7
			keyPressed = "7";
			break;
		case '8':
		case '_': // case 8
			keyPressed = "8";
			break;
		case '9':
		case 'ç': // case ç = 9
			keyPressed = "9";
			break;

		// ActionPan
		case '+': // case right -> +
			keyPressed = "+";
			break;
		case '*': // case keyPressedup -> x
			keyPressed = "x";
			break;
		case '/': // case keyPressed down -> /
			keyPressed = "/";
			break;
		default:
			switch(keyCode) {
			case 54: // case keycode 54 pressed(-)-> keypressed 6
			case 45: // case keycode 45 pressed(-)-> keypressed 6
				keyPressed = "6";
				break;
			case 39: // case keycode 39 pressed (right) -> +
				keyPressed = "+";
				break;
			case 109: // case keycode 109 -> keypressed -
			case 37 : // case keycode 37 pressed (left) -> -
				keyPressed = "-";
				break;
			case 38 : // case keycode 38 Pressed (up) -> x
				keyPressed = "x";
				break;
			case 40 : // case keycode 40 Pressed (down) -> /
				keyPressed = "/";
				break;
			case 10 : // case keycode 10 Pressed (CR) -> =
				keyPressed = "=";
				break;
			case 33: // case keycode 33 Pressed (page up) -> MC
				keyPressed = "MC";
				break;
			case 34: // case keycode 34 Pressed (page down) -> MR
				keyPressed = "MS";
				break;
			case 8: // case keycode 8 Pressed (backspace) -> C
				keyPressed = "<";
				break;
			case 127: // case keycode 127 Pressed (Suppr) -> Suppr
				keyPressed = "C";
				break;
			default: //logger.logging(">> --> not binded");
				return;
			}
			break;
		}
		logger.logging(">> KeyListener.keyCode(" + keyCode + ").keyPressed(" + keyText + ").keyChar(" + keyChar + ")");
		logger.logging(">> keyPressed replaced by : " + keyPressed + "");
		processKeytoButtonBinding(keyPressed);
	}

	@Override
	public void actionPerformed(ActionEvent ae) {
		// TODO Auto-generated method stub
		logger.logging(">> actionPerformed");
		// JButton buttonHit= (JButton) arg0.getSource();
		// String buttonTitle = buttonHit.getName();
		String buttonTitle = ae.getActionCommand();
		logger.logging(">> buttonTitle= " + buttonTitle);
		calculette.processAction(buttonTitle);
		this.requestFocus();
	}

}

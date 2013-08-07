package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SpringLayout;

public class LoginGUI extends JFrame {
	private static final long serialVersionUID = -295860552164986370L;
	private JPanel contentPane;
	private JTextField tf_name;
	private JLabel lblPseudo;
	private JLabel lblMotDePasse;
	private JPasswordField tf_pwd;

	/**
	 * Create the frame.
	 */
	public LoginGUI () {
		initComponent();

		contentPane = new JPanel();
		setContentPane(contentPane);
		SpringLayout sl_contentPane = new SpringLayout();
		contentPane.setLayout(sl_contentPane);

		tf_name = new JTextField();
		sl_contentPane.putConstraint(SpringLayout.WEST, tf_name, 10, SpringLayout.WEST, contentPane);
		sl_contentPane.putConstraint(SpringLayout.EAST, tf_name, -10, SpringLayout.EAST, contentPane);
		contentPane.add(tf_name);
		tf_name.setColumns(10);

		lblPseudo = new JLabel("Pseudonyme");
		sl_contentPane.putConstraint(SpringLayout.NORTH, lblPseudo, 10, SpringLayout.NORTH, contentPane);
		sl_contentPane.putConstraint(SpringLayout.WEST, lblPseudo, 10, SpringLayout.WEST, contentPane);
		sl_contentPane.putConstraint(SpringLayout.NORTH, tf_name, 6, SpringLayout.SOUTH, lblPseudo);
		contentPane.add(lblPseudo);

		lblMotDePasse = new JLabel("Mot de passe");
		sl_contentPane.putConstraint(SpringLayout.NORTH, lblMotDePasse, 10, SpringLayout.SOUTH, tf_name);
		sl_contentPane.putConstraint(SpringLayout.WEST, lblMotDePasse, 0, SpringLayout.WEST, tf_name);
		contentPane.add(lblMotDePasse);

		tf_pwd = new JPasswordField();
		sl_contentPane.putConstraint(SpringLayout.NORTH, tf_pwd, 6, SpringLayout.SOUTH, lblMotDePasse);
		sl_contentPane.putConstraint(SpringLayout.WEST, tf_pwd, 0, SpringLayout.WEST, tf_name);
		sl_contentPane.putConstraint(SpringLayout.EAST, tf_pwd, -10, SpringLayout.EAST, contentPane);
		tf_pwd.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if ( e.getKeyCode() != KeyEvent.VK_ENTER ) { return; }
				connection();
			}
		});
		contentPane.add(tf_pwd);

		JButton btnConnexion = new JButton("Connexion");
		sl_contentPane.putConstraint(SpringLayout.NORTH, btnConnexion, 17, SpringLayout.SOUTH, tf_pwd);
		sl_contentPane.putConstraint(SpringLayout.WEST, btnConnexion, 67, SpringLayout.WEST, contentPane);
		btnConnexion.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				connection();
			}
		});
		contentPane.add(btnConnexion);

		setVisible(true);
	}

	private void initComponent() {
		setSize(235, 177);
		setResizable(false);
		setLocationRelativeTo(null);
		setTitle("Accueil");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	/**
	 * Méthode de connexion commune au mode hors ligne et en ligne.
	 */
	private void connection() {
		goMainGUI();
	}

	/**
	 * Changement de fenetre vers la principale
	 */
	public void goMainGUI() {
		setVisible(false);
		MainGUI.getInstance().setVisible(true);
	}
}

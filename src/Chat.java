import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Calendar;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

@SuppressWarnings("serial")
public class Chat extends UnicastRemoteObject implements ClientInterf {
	private JFrame janela;
	private JPanel painel;
	private JTextArea jTextArea;
	private JTextField jTextField, jTextField2;
	private JButton btnEnviar, btnOK;
	private ClientInterf stub;
	private JLabel jlabel;

	public Chat() throws RemoteException {
		janela = new JFrame("Cliente");
		janela.setSize(480, 480);
		janela.setVisible(true);
		janela.setResizable(false);

		painel = new JPanel();
		painel.setSize(600, 260);
		painel.setVisible(true);
		painel.setBackground(java.awt.Color.gray);

		jTextArea = new JTextArea(20, 40);
		jTextArea.setEnabled(true);
		jTextArea.setVisible(true);
		painel.add(jTextArea);

		jlabel = new JLabel("Digite seu nome: ");
		jlabel.setVisible(true);
		painel.add(jlabel);

		jTextField2 = new JTextField(10);
		jTextField2.setVisible(true);
		painel.add(jTextField2);

		btnOK = new JButton("OK");
		btnOK.setVisible(true);
		btnOK.setSize(30, 20);
		painel.add(btnOK);

		jTextField = new JTextField(25);
		painel.add(jTextField);

		btnEnviar = new JButton("Enviar");
		btnEnviar.setEnabled(false);
		// btnEnviar.setVisible(false);
		btnEnviar.setSize(30, 20);
		painel.add(btnEnviar);

		janela.add(painel);
		janela.validate();

		janela.addWindowListener(new WindowListener() {

			@Override
			public void windowOpened(WindowEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void windowIconified(WindowEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void windowDeiconified(WindowEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void windowDeactivated(WindowEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void windowClosing(WindowEvent e) {
				try {
					stub.sairChat();
				} catch (RemoteException e1) {
					e1.printStackTrace();
				}
			}

			@Override
			public void windowClosed(WindowEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void windowActivated(WindowEvent e) {
				// TODO Auto-generated method stub

			}
		});

		btnEnviar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {

					String mensagem = jTextField.getText();
					String nome = jTextField2.getText();
					stub.atualizarMensagem(mensagem, nome);
					Calendar hora = Calendar.getInstance();

					jTextArea.append(nome + " às "
							+ hora.get(Calendar.HOUR_OF_DAY) + ":"
							+ hora.get(Calendar.MINUTE) + " disse: " + mensagem
							+ "\n");

					jTextField.setText(null);

				} catch (RemoteException e1) {
					e1.printStackTrace();
				}
			}
		});

		btnOK.addActionListener(new ActionListener() {
			@SuppressWarnings("deprecation")
			@Override
			public void actionPerformed(ActionEvent e) {

				jTextField2.disable();
				btnEnviar.setEnabled(true);
				// btnEnviar.setVisible(true);
			}
		});
	}

	@Override
	public void atualizarMensagem(String mensagem, String nome)
			throws RemoteException {
		jTextArea.append(nome + mensagem + "\n");
	}

	@Override
	public void definirStub(ClientInterf chat) throws RemoteException {
		this.stub = chat;
	}

	@Override
	public void sairChat() throws RemoteException {
		janela.dispose();
	}

}

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Calendar;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;

import javax.swing.JOptionPane;

public class Client {

	@FXML
	private ResourceBundle resources;

	@FXML
	private URL location;

	@FXML
	private Label lblConexao, lblData, lblArmazena, lblEnvVid, lblZipar;

	@FXML
	private Text txtMatInv1, txtMatInv2, txtMatInv3, txtMatInv4, txtMatInv5,
			txtMatInv6, txtMatInv7, txtMatInv8;

	@FXML
	private Text txtMatInv9, txtMatInv10, txtMatInv11, txtMatInv12,
			txtMatInv13, txtMatInv14, txtMatInv15, txtMatInv16;

	@FXML
	private Text txtEnviadoArm, txtEnviadoEnvVid, txtEnviadoModVid,
			txtEnviadoZip;

	@FXML
	private TextField tfCaminhoArmaze, tfCaminhoModVid, tfCaminhoZip,
			tfCaminhoZip2, tfCaminhoEnvVid;

	@FXML
	private TextField tfNum1, tfNum2, tfNum3, tfNum4, tfNum5, tfNum6, tfNum7,
			tfNum8;

	@FXML
	private TextField tfNum9, tfNum10, tfNum11, tfNum12, tfNum13, tfNum14,
			tfNum15, tfNum16;

	@FXML
	private TextField tfPorta;

	@FXML
	private TextField tfServidor;

	@FXML
	private ToggleGroup tipoMatriz;

	@FXML
	private TextField tfChat;

	@FXML
	private TextArea taChat;

	@FXML
	private Button btnIniciarChat;

	@FXML
	private MediaView mvVideo;
	private Media media;
	private MediaPlayer mediaPlayer;

	private Metodos stub;
	private File file, file2;
	private byte[] arquivo;
	private TextField[][] camposMatriz = new TextField[4][4];
	private Text[][] txtMatrizInv = new Text[4][4];
	private double[][] numMatriz, matrizInv;
	private int matrizOp = 4;
	private boolean preenchido = true;

	@FXML
	void initialize() {
		camposMatriz[0][0] = tfNum1;
		camposMatriz[0][1] = tfNum2;
		camposMatriz[0][2] = tfNum3;
		camposMatriz[0][3] = tfNum4;
		camposMatriz[1][0] = tfNum5;
		camposMatriz[1][1] = tfNum6;
		camposMatriz[1][2] = tfNum7;
		camposMatriz[1][3] = tfNum8;
		camposMatriz[2][0] = tfNum9;
		camposMatriz[2][1] = tfNum10;
		camposMatriz[2][2] = tfNum11;
		camposMatriz[2][3] = tfNum12;
		camposMatriz[3][0] = tfNum13;
		camposMatriz[3][1] = tfNum14;
		camposMatriz[3][2] = tfNum15;
		camposMatriz[3][3] = tfNum16;

		txtMatrizInv[0][0] = txtMatInv1;
		txtMatrizInv[0][1] = txtMatInv2;
		txtMatrizInv[0][2] = txtMatInv3;
		txtMatrizInv[0][3] = txtMatInv4;
		txtMatrizInv[1][0] = txtMatInv5;
		txtMatrizInv[1][1] = txtMatInv6;
		txtMatrizInv[1][2] = txtMatInv7;
		txtMatrizInv[1][3] = txtMatInv8;
		txtMatrizInv[2][0] = txtMatInv9;
		txtMatrizInv[2][1] = txtMatInv10;
		txtMatrizInv[2][2] = txtMatInv11;
		txtMatrizInv[2][3] = txtMatInv12;
		txtMatrizInv[3][0] = txtMatInv13;
		txtMatrizInv[3][1] = txtMatInv14;
		txtMatrizInv[3][2] = txtMatInv15;
		txtMatrizInv[3][3] = txtMatInv16;

	}

	@FXML
	void btnConectarOnAction(ActionEvent event) {
		try {
			Registry registro = LocateRegistry.getRegistry(
					tfServidor.getText(), Integer.valueOf(tfPorta.getText()));
			stub = (Metodos) registro.lookup("RMI");
			lblConexao.setText("Conectado");
			lblConexao.setTextFill(Color.GREEN);

			Calendar data = Calendar.getInstance();
			lblData.setText("Data: " + data.get(Calendar.DAY_OF_MONTH) + "/"
					+ data.get(Calendar.MONTH) + "/" + data.get(Calendar.YEAR)
					+ "                 Hora: "
					+ data.get(Calendar.HOUR_OF_DAY) + ":"
					+ data.get(Calendar.MINUTE));

		} catch (RemoteException | NotBoundException e) {
			e.printStackTrace();
			lblConexao.setText("Erro ao se conectar.");
			lblConexao.setTextFill(Color.RED);
		}
	}

	@FXML
	void btnSelecionarArmazeOnAction(ActionEvent event) throws IOException {
		FileChooser fileChooser = new FileChooser();
		file = fileChooser.showOpenDialog(null);
		if (file != null) {
			tfCaminhoArmaze.setText(file.toURI().toURL().toString());
		}
	}

	@FXML
	void btnEnviarArmazOnAction(ActionEvent event) {
		arquivo = null;
		if (file != null) {
			arquivo = new byte[(int) file.length()];
			FileInputStream fis;
			try {
				fis = new FileInputStream(file);
				fis.read(arquivo);
				fis.close();

				stub.armazenaArquivo(arquivo, file.getName());
				lblArmazena.setText("Enviado com sucesso!");
				lblArmazena.setTextFill(Color.GREEN);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		file = null;
	}

	@FXML
	void btnSelecionarZipOnAction(ActionEvent event) throws IOException {
		FileChooser fileChooser = new FileChooser();
		file = fileChooser.showOpenDialog(null);
		if (file != null) {
			tfCaminhoZip.setText(file.toURI().toURL().toString());
		}
	}

	@FXML
	void btnSelecionarZipDoisOnAction(ActionEvent event) throws IOException {
		FileChooser fileChooser = new FileChooser();
		file2 = fileChooser.showOpenDialog(null);
		if (file2 != null) {
			tfCaminhoZip2.setText(file2.toURI().toURL().toString());
		}
	}

	@FXML
	void btnEnviarZipOnAction(ActionEvent event) {
		if ((file != null) && (file2 != null)) {
			File[] files = { file, file2 };
			byte[][] arquivos = { new byte[(int) file.length()],
					new byte[(int) file2.length()] };
			try {

				for (int i = 0; i < arquivos.length; i++) {
					FileInputStream fis = new FileInputStream(files[i]);
					fis.read(arquivos[i], 0, arquivos[i].length);
					fis.close();
				}

				byte[] zip = stub.zipar(arquivos[0], file.getName(),
						arquivos[1], file2.getName());

				lblZipar.setText("Arquivo enviado e compactado com sucesso.");
				lblZipar.setTextFill(Color.GREEN);

				File fileZip = new File("arquivo.zip");
				fileZip.createNewFile();

				FileOutputStream fos = new FileOutputStream(fileZip);
				fos.write(zip);
				fos.flush();
				fos.close();

			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	@FXML
	void btnSelecionarModVidOnAction(ActionEvent event)
			throws MalformedURLException {
		FileChooser fileChooser = new FileChooser();
		file = fileChooser.showOpenDialog(null);
		if (file != null) {
			tfCaminhoModVid.setText(file.toURI().toURL().toString());
		}
	}

	@FXML
	void btnEnviarModVidOnAction(ActionEvent event) {
		arquivo = null;
		if (file != null) {
			arquivo = new byte[(int) file.length()];
			FileInputStream fis;
			try {
				fis = new FileInputStream(file);
				fis.read(arquivo);
				fis.close();

				byte[] videoMod = stub.modificaVideo(arquivo, file.getName());

				File fileVideoMod = new File(file.getName());
				fileVideoMod.createNewFile();

				FileOutputStream fos = new FileOutputStream(fileVideoMod);
				fos.write(videoMod);
				fos.flush();
				fos.close();

				media = new Media(file.toURI().toURL().toString());
				mediaPlayer = new MediaPlayer(media);
				mediaPlayer.play();
				mvVideo.setMediaPlayer(mediaPlayer);

			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		file = null;
	}

	@FXML
	void btnSelecionarEnvVidOnAction(ActionEvent event)
			throws MalformedURLException {
		FileChooser fileChooser = new FileChooser();
		file = fileChooser.showOpenDialog(null);
		if (file != null) {
			tfCaminhoEnvVid.setText(file.toURI().toURL().toString());
		}
	}

	@FXML
	void btnEnviarEnvVidOnAction(ActionEvent event) {
		try {
			arquivo = null;
			if (file != null) {
				arquivo = new byte[(int) file.length()];
				FileInputStream fis = new FileInputStream(file);
				fis.read(arquivo);
				fis.close();

				stub.executaVideo(arquivo, file.getName());
				lblEnvVid.setText("Video enviado com sucesso");
				lblEnvVid.setTextFill(Color.GREEN);
			}

			file = null;
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
		}
	}

	@FXML
	void rb2x2OnAction(ActionEvent event) {
		limpaCampos();
		for (int i = 0; i < camposMatriz.length; i++) {
			for (int j = 0; j < camposMatriz.length; j++) {
				if (!(((i == 0) && (j == 0)) || (i == 0) && (j == 1)
						|| (i == 1) && (j == 0) || (i == 1) && (j == 1))) {
					camposMatriz[i][j].setVisible(false);
					txtMatrizInv[i][j].setVisible(false);
				}
			}
		}
		matrizOp = 2;
	}

	@FXML
	void rb3x3OnAction(ActionEvent event) {
		limpaCampos();
		for (int i = 0; i < camposMatriz.length; i++) {
			for (int j = 0; j < camposMatriz.length; j++) {
				if ((((i == 3) && (j == 0)) || (i == 3) && (j == 1) || (i == 3)
						&& (j == 2) || (i == 3) && (j == 3) || (i == 0)
						&& (j == 3) || (i == 1) && (j == 3) || (i == 2)
						&& (j == 3))) {
					camposMatriz[i][j].setVisible(false);
					txtMatrizInv[i][j].setVisible(false);
				} else {
					camposMatriz[i][j].setVisible(true);
					txtMatrizInv[i][j].setVisible(true);
				}
			}
		}
		matrizOp = 3;
	}

	@FXML
	void rb4x4OnAction(ActionEvent event) {
		limpaCampos();
		for (int i = 0; i < camposMatriz.length; i++) {
			for (int j = 0; j < camposMatriz.length; j++) {
				camposMatriz[i][j].setVisible(true);
				txtMatrizInv[i][j].setVisible(true);
			}
		}
		matrizOp = 4;
	}

	@FXML
	void btnCalcularInvOnAction(ActionEvent event) {
		numMatriz = new double[matrizOp][matrizOp];

		for (int i = 0; i < matrizOp; i++) {
			for (int j = 0; j < matrizOp; j++) {
				if (camposMatriz[i][j].getText().trim().isEmpty()) {
					preenchido = false;
					continue;
				} else {
					numMatriz[i][j] = Double.parseDouble(camposMatriz[i][j]
							.getText());
					preenchido = true;
				}
			}
		}

		if (preenchido) {
			try {
				matrizInv = stub.matrizInversa(numMatriz);
				for (int i = 0; i < matrizOp; i++) {
					for (int j = 0; j < matrizOp; j++) {
						txtMatrizInv[i][j].setText(String.format("%.2f",
								matrizInv[i][j]));
					}
				}
			} catch (RemoteException | NullPointerException e) {
				txtMatrizInv[0][0].setText("Conexão está com problemas.");
			} catch (RuntimeException e) {
				txtMatrizInv[0][0].setText("Matriz inversa não existe.");
			}
			preenchido = false;
		} else {
			txtMatrizInv[0][0].setText("Preencha todos os campos.");
		}

	}

	void limpaCampos() {
		for (int i = 0; i < camposMatriz.length; i++) {
			for (int j = 0; j < camposMatriz.length; j++) {
				camposMatriz[i][j].setText("");
				txtMatrizInv[i][j].setText("");
			}
		}
	}

	boolean perm = true;

	@FXML
	void btnIniciarChatOnAction(ActionEvent event) {

		try {
			perm = stub.chatReq();
			if (!perm) {
				JOptionPane.showMessageDialog(null,
						"Número máximo de requisições alcançado");
			} else {
				Chat chat = new Chat();
				stub.chat(chat);

				btnIniciarChat.setDisable(true);
			}
		} catch (RemoteException e1) {
			e1.printStackTrace();
		}
	}

	@FXML
	void btnEnviarChatOnAction(ActionEvent event) throws RemoteException {

	}

}
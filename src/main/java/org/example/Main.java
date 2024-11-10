package org.example;
import org.example.adocao.Adocao;
import org.example.adocao.AdocaoRepository;
import org.example.adocao.AdocaoService;
import org.example.adotante.Adotante;
import org.example.adotante.AdotanteRepository;
import org.example.adotante.AdotanteService;
import org.example.animal.Animal;
import org.example.animal.AnimalRepository;
import org.example.animal.AnimalService;
import org.example.funcionario.Funcionario;
import org.example.funcionario.FuncionarioRepository;
import org.example.funcionario.FuncionarioService;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.SQLException;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

public class Main {
	private static AnimalService animalService;

    static {
        try {
            animalService = new AnimalService(new AnimalRepository("jdbc:mysql://localhost:3306/adocao","root",""));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static AdotanteService adotanteService;

    static {
        try {
            adotanteService = new AdotanteService(new AdotanteRepository("jdbc:mysql://localhost:3306/adocao","root",""));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static FuncionarioService funcionarioService;

    static {
        try {
            funcionarioService = new FuncionarioService(new FuncionarioRepository("jdbc:mysql://localhost:3306/adocao","root",""));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static AdocaoService adocaoService;

    static {
        try {
            adocaoService = new AdocaoService(new AdocaoRepository("jdbc:mysql://localhost:3306/adocao","root",""));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) throws SQLException {
		SwingUtilities.invokeLater(() -> criarMenuPrincipal());
	}

	public static void criarMenuPrincipal() {

		// Definir o look-and-feel do sistema
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		JFrame frame = new JFrame("Menu Principal");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(600, 400);
		frame.setLocationRelativeTo(null); // Centralizar a janela

		// Painel principal com GridBagLayout
		JPanel panel = new JPanel(new GridBagLayout());
		panel.setBackground(new Color(226, 216, 194));
		panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); // Margens
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(10, 10, 10, 10); // Espaçamento entre componentes

		ImageIcon originalIcon = new ImageIcon("./out/resources/logo.png");
		Image originalImage = originalIcon.getImage();
		Image resizedImage = originalImage.getScaledInstance(150, 150, Image.SCALE_SMOOTH);
		ImageIcon logoIcon = new ImageIcon(resizedImage);

		JLabel logoLabel = new JLabel(logoIcon);
		logoLabel.setHorizontalAlignment(JLabel.CENTER);
		logoLabel.setVerticalAlignment(JLabel.TOP);

		// Configuração do GridBagConstraints para centralizar a logo
		GridBagConstraints imgGbc = new GridBagConstraints();
		imgGbc.gridwidth = 3; // largura do espaço para centralizar
		imgGbc.insets = new Insets(10, 0, 10, 0); // margem superior e inferior
		imgGbc.anchor = GridBagConstraints.CENTER;
		panel.add(logoLabel, imgGbc);
		
        // Criar botões com fontes personalizadas
		Font font = new Font("Arial", Font.BOLD, 14);

		JButton btnGerenciarAnimais = new JButton("Gerenciar Animais");
		btnGerenciarAnimais.setFont(font);

		JButton btnGerenciarAdotantes = new JButton("Gerenciar Adotantes");
		btnGerenciarAdotantes.setFont(font);

		JButton btnGerenciarFuncionarios = new JButton("Gerenciar Funcionários");
		btnGerenciarFuncionarios.setFont(font);

		JButton btnGerenciarAdocoes = new JButton("Gerenciar Adoções");
		btnGerenciarAdocoes.setFont(font);

		JButton btnEncerrar = new JButton("Encerrar");
		btnEncerrar.setFont(font);
		btnEncerrar.setForeground(Color.BLACK);

		// Adicionar listeners
		btnGerenciarAnimais.addActionListener(e -> {
            try {
                gerenciarAnimais();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        });
		btnGerenciarAdotantes.addActionListener(e -> {
            try {
                gerenciarAdotantes();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        });
		btnGerenciarFuncionarios.addActionListener(e -> {
            try {
                gerenciarFuncionarios();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        });
		btnGerenciarAdocoes.addActionListener(e -> {
            try {
                gerenciarAdocoes();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        });
		btnEncerrar.addActionListener(e -> System.exit(0));

		// Posicionar botões no painel
		gbc.gridx = 1;
		gbc.gridy = 1;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		panel.add(btnGerenciarAnimais, gbc);

		gbc.gridx = 2;
		gbc.gridy = 1;
		panel.add(btnGerenciarAdotantes, gbc);

		gbc.gridx = 1;
		gbc.gridy = 2;
		panel.add(btnGerenciarFuncionarios, gbc);

		gbc.gridx = 2;
		gbc.gridy = 2;
		panel.add(btnGerenciarAdocoes, gbc);

		// Botão Encerrar ocupa as duas colunas na parte inferior
		gbc.gridx = 1;
		gbc.gridy = 3;
		gbc.gridwidth = 2;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		panel.add(btnEncerrar, gbc);

		// Adicionar painel ao frame
		frame.add(panel);
		frame.setVisible(true);

	}

	public static void gerenciarAnimais() throws SQLException {
		String[] options = { "Criar Animal", "Listar Animais", "Atualizar Animal", "Deletar Animal", "Voltar" };
		int choice = JOptionPane.showOptionDialog(null, "Escolha uma opção:", "Gerenciar Animais",
				JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);

		switch (choice) {
		case 0:
			criarAnimal();
			break;
		case 1:
			exibirAnimaisPanel();
			animalService.listarAnimais();
			break;
		case 2:
			atualizarAnimal();
			break;
		case 3:
			deletarAnimal();
			break;
		default:
			break;
		}
	}
	
	public static void exibirAnimaisPanel() throws SQLException {
        // Obter a lista de animais do serviço
		List<Animal> animais = animalService.listarAnimais();
		
        // Criação do painel principal
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(226, 216, 194)); // Definindo a cor de fundo

        // Configuração da tabela
        String[] colunas = {"Id","Nome", "Espécie", "Raça", "Idade (meses)", "Castrado"};
        DefaultTableModel modeloTabela = new DefaultTableModel(colunas, 0);
        JTable tabela = new JTable(modeloTabela);
        
        // Preencher a tabela com os dados dos animais
        for (Animal animal : animais) {
            Object[] linha = {
				animal.getId(),
                animal.getNome(),
                animal.getEspecie(),
                animal.getRaca(),
                animal.getIdade(),
                animal.isCastrado() ? "Sim" : "Não"
            };
            modeloTabela.addRow(linha);
        }
        
        // Adicionar a tabela a um JScrollPane para rolagem
        JScrollPane scrollPane = new JScrollPane(tabela);
        tabela.setFillsViewportHeight(true);

        // Adicionando componentes ao painel
        panel.add(scrollPane, BorderLayout.CENTER);

        // Exibir o painel em um JOptionPane
        JOptionPane.showMessageDialog(null, panel, "Lista de Animais Cadastrados", JOptionPane.PLAIN_MESSAGE);
    }

	private static void criarAnimal() {
        // Criação do painel principal
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(5, 2, 10, 10));

        // Campos de entrada
        JLabel nomeLabel = new JLabel("Nome do Animal:");
        JTextField nomeField = new JTextField();

        JLabel especieLabel = new JLabel("Espécie:");
        JTextField especieField = new JTextField();

        JLabel racaLabel = new JLabel("Raça:");
        JTextField racaField = new JTextField();

        JLabel idadeLabel = new JLabel("Idade (em meses):");
        JTextField idadeField = new JTextField();

        JLabel castradoLabel = new JLabel("Animal é castrado?");
        JCheckBox castradoCheckBox = new JCheckBox();

        // Adicionando componentes ao painel
        panel.add(nomeLabel);
        panel.add(nomeField);
        panel.add(especieLabel);
        panel.add(especieField);
        panel.add(racaLabel);
        panel.add(racaField);
        panel.add(idadeLabel);
        panel.add(idadeField);
        panel.add(castradoLabel);
        panel.add(castradoCheckBox);

        // Botão para salvar o animal
        int result = JOptionPane.showConfirmDialog(null, panel, "Cadastro de Animal", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            try {
                String nome = nomeField.getText();
                String especie = especieField.getText();
                String raca = racaField.getText();
                int idade = Integer.parseInt(idadeField.getText());
                boolean castrado = castradoCheckBox.isSelected();

                Animal animal = new Animal(nome, especie, raca, idade, castrado);
                animalService.adicionarAnimal(animal);
                JOptionPane.showMessageDialog(null, "Animal cadastrado com sucesso.");
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Por favor, insira uma idade válida em meses.");
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
	}

	private static void atualizarAnimal() throws SQLException {
        // Solicitar o ID do animal a ser atualizado
        int idAtualizar = Integer.parseInt(JOptionPane.showInputDialog("ID do Animal a ser atualizado:"));
		Optional <Animal> animalParaAtualizar = animalService.buscarAnimalPorId(idAtualizar);

        if (animalParaAtualizar.isPresent()) {
			Animal animal = animalParaAtualizar.get();
            // Criação do painel principal
            JPanel panel = new JPanel();
            panel.setLayout(new GridLayout(5, 2, 10, 10));

            // Campos de entrada com os dados atuais do animal
            JLabel nomeLabel = new JLabel("Nome:");
            JTextField nomeField = new JTextField(animal.getNome());

            JLabel especieLabel = new JLabel("Espécie:");
            JTextField especieField = new JTextField(animal.getEspecie());

            JLabel racaLabel = new JLabel("Raça:");
            JTextField racaField = new JTextField(animal.getRaca());

            JLabel idadeLabel = new JLabel("Idade (em meses):");
            JTextField idadeField = new JTextField(String.valueOf(animal.getIdade()));

            JLabel castradoLabel = new JLabel("Animal é castrado?");
            JCheckBox castradoCheckBox = new JCheckBox();
            castradoCheckBox.setSelected(animal.isCastrado());

            // Adicionando componentes ao painel
            panel.add(nomeLabel);
            panel.add(nomeField);
            panel.add(especieLabel);
            panel.add(especieField);
            panel.add(racaLabel);
            panel.add(racaField);
            panel.add(idadeLabel);
            panel.add(idadeField);
            panel.add(castradoLabel);
            panel.add(castradoCheckBox);

            // Exibir o painel em um JOptionPane
            int result = JOptionPane.showConfirmDialog(null, panel, "Atualizar Animal", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

            if (result == JOptionPane.OK_OPTION) {
                try {
                    // Atualizar os valores do animal com os novos dados inseridos
                    animal.setNome(nomeField.getText());
                    animal.setEspecie(especieField.getText());
                    animal.setRaca(racaField.getText());
                    animal.setIdade(Integer.parseInt(idadeField.getText()));
                    animal.setCastrado(castradoCheckBox.isSelected());
					animalService.atualizarAnimal(animal);

                    // Confirmar a atualização
                    JOptionPane.showMessageDialog(null, "Animal atualizado com sucesso.");
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(null, "Por favor, insira uma idade válida em meses.");
                }
            }
        } else {
            JOptionPane.showMessageDialog(null, "Animal não encontrado.");
        }
    }
	
	private static void deletarAnimal() throws SQLException {
        // Solicitar o ID do animal a ser removido
        int idRemover = Integer.parseInt(JOptionPane.showInputDialog("ID do Animal a ser removido:"));
        Optional <Animal> animalParaRemover = animalService.buscarAnimalPorId(idRemover);

        if (animalParaRemover.isPresent()) {
			Animal animal = animalParaRemover.get();
            // Criação do painel principal
            JPanel panel = new JPanel();
            panel.setLayout(new GridLayout(5, 2, 10, 10));
            panel.setBackground(new Color(226, 216, 194)); // Definindo a cor de fundo

            // Exibição dos dados do animal
            JLabel nomeLabel = new JLabel("Nome:");
            JLabel nomeValue = new JLabel(animal.getNome());

            JLabel especieLabel = new JLabel("Espécie:");
            JLabel especieValue = new JLabel(animal.getEspecie());

            JLabel racaLabel = new JLabel("Raça:");
            JLabel racaValue = new JLabel(animal.getRaca());

            JLabel idadeLabel = new JLabel("Idade (em meses):");
            JLabel idadeValue = new JLabel(String.valueOf(animal.getIdade()));

            JLabel castradoLabel = new JLabel("Animal é castrado?");
            JLabel castradoValue = new JLabel(animal.isCastrado() ? "Sim" : "Não");

            // Adicionando componentes ao painel
            panel.add(nomeLabel);
            panel.add(nomeValue);
            panel.add(especieLabel);
            panel.add(especieValue);
            panel.add(racaLabel);
            panel.add(racaValue);
            panel.add(idadeLabel);
            panel.add(idadeValue);
            panel.add(castradoLabel);
            panel.add(castradoValue);

            // Exibir o painel em um JOptionPane para confirmar exclusão
            int result = JOptionPane.showConfirmDialog(null, panel, "Excluir Animal", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

            if (result == JOptionPane.OK_OPTION) {
                // Confirmar a exclusão
                animalService.removerAnimal(idRemover);
                JOptionPane.showMessageDialog(null, "Animal removido com sucesso.");
            } else {
                JOptionPane.showMessageDialog(null, "Ação de exclusão cancelada.");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Animal não encontrado.");
        }
    }

	public static void gerenciarAdotantes() throws SQLException {
		String[] options = { "Criar Adotante", "Listar Adotantes", "Atualizar Adotante", "Deletar Adotante", "Voltar" };
		int choice = JOptionPane.showOptionDialog(null, "Escolha uma opção:", "Gerenciar Adotantes",
				JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);

		switch (choice) {
		case 0:
			criarAdotante();
			break;
		case 1:
			exibirAdotantesPanel();
			break;
		case 2:
			atualizarAdotante();
			break;
		case 3:
			deletarAdotante();
			break;
		default:
			break;
		}
	}

	private static void criarAdotante() {
		// Criação do painel principal
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(4, 2, 10, 10));

		// Campos de entrada
		JLabel nomeLabel = new JLabel("Nome:");
		JTextField nomeField = new JTextField();

		JLabel idadeLabel = new JLabel("Idade:");
		JTextField idadeField = new JTextField();

		JLabel cpfLabel = new JLabel("CPF:");
		JTextField cpfField = new JTextField();

		JLabel enderecoLabel = new JLabel("Endereço:");
		JTextField enderecoField = new JTextField();

		// Adicionando componentes ao painel
		panel.add(nomeLabel);
		panel.add(nomeField);
		panel.add(idadeLabel);
		panel.add(idadeField);
		panel.add(cpfLabel);
		panel.add(cpfField);
		panel.add(enderecoLabel);
		panel.add(enderecoField);

		// Botão para salvar o adotante
		int result = JOptionPane.showConfirmDialog(null, panel, "Cadastro de Adotante", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

		if (result == JOptionPane.OK_OPTION) {
			try {
				String nome = nomeField.getText();
				int idade = Integer.parseInt(idadeField.getText());
				String cpf = cpfField.getText();
				String endereco = enderecoField.getText();

				Adotante adotante = new Adotante(nome, idade, cpf, endereco);
				adotanteService.adicionarAdotante(adotante);
				JOptionPane.showMessageDialog(null, "Adotante cadastrado com sucesso.");
			} catch (NumberFormatException e) {
				JOptionPane.showMessageDialog(null, "Por favor, insira uma idade válida.");
			} catch (SQLException e){
				JOptionPane.showMessageDialog(null, "Erro ao fazer a consulta no banco de dados.");
			}
		}
	}

	public static void exibirAdotantesPanel() throws SQLException {
		// Obter a lista de adotantes do serviço
		List<Adotante> adotantes = adotanteService.listarAdotantes();

		// Criação do painel principal
		JPanel panel = new JPanel(new BorderLayout());
		panel.setBackground(new Color(226, 216, 194)); // Definindo a cor de fundo

		// Configuração da tabela
		String[] colunas = {"Id","Nome", "Idade", "CPF", "Endereço"};
		DefaultTableModel modeloTabela = new DefaultTableModel(colunas, 0);
		JTable tabela = new JTable(modeloTabela);

		// Preencher a tabela com os dados dos adotantes
		for (Adotante adotante : adotantes) {
			Object[] linha = {
					adotante.getId(),
					adotante.getNome(),
					adotante.getIdade(),
					adotante.getCpf(),
					adotante.getEndereco()
			};
			modeloTabela.addRow(linha);
		}

		// Adicionar a tabela a um JScrollPane para rolagem
		JScrollPane scrollPane = new JScrollPane(tabela);
		tabela.setFillsViewportHeight(true);

		// Adicionando componentes ao painel
		panel.add(scrollPane, BorderLayout.CENTER);

		// Exibir o painel em um JOptionPane
		JOptionPane.showMessageDialog(null, panel, "Lista de Adotantes Cadastrados", JOptionPane.PLAIN_MESSAGE);
	}


	private static void atualizarAdotante() throws SQLException {
		String cpf = JOptionPane.showInputDialog("CPF do Adotante a ser atualizado:");
		Optional<Adotante> adotanteParaAtualizar = adotanteService.buscarAdotantePorCpf(cpf);
		if (adotanteParaAtualizar.isPresent()) {
			Adotante adotante = adotanteParaAtualizar.get();
			adotante.setNome(JOptionPane.showInputDialog("Novo Nome:", adotante.getNome()));
			adotante.setEndereco(JOptionPane.showInputDialog("Novo Endereço:", adotante.getEndereco()));
			adotanteService.atualizarAdotante(adotante);
			JOptionPane.showMessageDialog(null, "Adotante atualizado com sucesso.");
		} else {
			JOptionPane.showMessageDialog(null, "Adotante não encontrado.");
		}
	}

	private static void deletarAdotante() throws SQLException {
		// Solicitar o CPF do adotante a ser removido
		String cpf = JOptionPane.showInputDialog("CPF do Adotante a ser removido:");
		Optional<Adotante> adotanteParaRemover = adotanteService.buscarAdotantePorCpf(cpf);

		if (adotanteParaRemover.isPresent()) {
			Adotante adotante = adotanteParaRemover.get();

			// Criação do painel principal
			JPanel panel = new JPanel();
			panel.setLayout(new GridLayout(5, 2, 10, 10));
			panel.setBackground(new Color(226, 216, 194)); // Definindo a cor de fundo

			// Exibição dos dados do adotante
			JLabel nomeLabel = new JLabel("Nome:");
			JLabel nomeValue = new JLabel(adotante.getNome());

			JLabel cpfLabel = new JLabel("CPF:");
			JLabel cpfValue = new JLabel(adotante.getCpf());

			JLabel enderecoLabel = new JLabel("Endereço:");
			JLabel enderecoValue = new JLabel(adotante.getEndereco());


			// Adicionando componentes ao painel
			panel.add(nomeLabel);
			panel.add(nomeValue);
			panel.add(cpfLabel);
			panel.add(cpfValue);
			panel.add(enderecoLabel);
			panel.add(enderecoValue);

			// Exibir o painel em um JOptionPane para confirmar exclusão
			int result = JOptionPane.showConfirmDialog(null, panel, "Excluir Adotante", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

			if (result == JOptionPane.OK_OPTION) {
				// Confirmar a exclusão
				adotanteService.removerAdotante(cpf);
				JOptionPane.showMessageDialog(null, "Adotante removido com sucesso.");
			} else {
				JOptionPane.showMessageDialog(null, "Ação de exclusão cancelada.");
			}
		} else {
			JOptionPane.showMessageDialog(null, "Adotante não encontrado.");
		}
	}


	// Gerenciamento de Funcionários
	public static void gerenciarFuncionarios() throws SQLException {
		String[] options = { "Criar Funcionário", "Listar Funcionários", "Atualizar Funcionário", "Deletar Funcionário",
				"Voltar" };	
		int choice = JOptionPane.showOptionDialog(null, "Escolha uma opção:", "Gerenciar Funcionários",
				JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);

		switch (choice) {
		case 0:
			criarFuncionario();
			break;
		case 1:
			exibirFuncionariosPanel();
			break;
		case 2:
			atualizarFuncionario();
			break;
		case 3:
			deletarFuncionario();
			break;
		default:
			break;
		}
	}

	private static void criarFuncionario() {
		// Criação do painel principal
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(4, 2, 10, 10));

		// Campos de entrada
		JLabel nomeLabel = new JLabel("Nome do Funcionário:");
		JTextField nomeField = new JTextField();

		JLabel idadeLabel = new JLabel("Idade:");
		JTextField idadeField = new JTextField();

		JLabel cpfLabel = new JLabel("CPF:");
		JTextField cpfField = new JTextField();

		JLabel enderecoLabel = new JLabel("Endereço:");
		JTextField enderecoField = new JTextField();

		// Adicionando componentes ao painel
		panel.add(nomeLabel);
		panel.add(nomeField);
		panel.add(idadeLabel);
		panel.add(idadeField);
		panel.add(cpfLabel);
		panel.add(cpfField);
		panel.add(enderecoLabel);
		panel.add(enderecoField);

		// Botão para salvar o funcionário
		int result = JOptionPane.showConfirmDialog(null, panel, "Cadastro de Funcionário", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

		if (result == JOptionPane.OK_OPTION) {
			try {
				String nome = nomeField.getText();
				int idade = Integer.parseInt(idadeField.getText());
				String cpf = cpfField.getText();
				String endereco = enderecoField.getText();

				Funcionario funcionario = new Funcionario(nome, idade, endereco, cpf);
				funcionarioService.adicionarFuncionario(funcionario);
				JOptionPane.showMessageDialog(null, "Funcionário cadastrado com sucesso.");
			} catch (NumberFormatException e) {
				JOptionPane.showMessageDialog(null, "Por favor, insira uma idade válida.");
			} catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
	}

	public static void exibirFuncionariosPanel() throws SQLException {
		// Obter a lista de funcionários do serviço
		List<Funcionario> funcionarios = funcionarioService.listarFuncionarios();

		// Criação do painel principal
		JPanel panel = new JPanel(new BorderLayout());
		panel.setBackground(new Color(226, 216, 194)); // Definindo a cor de fundo

		// Configuração da tabela
		String[] colunas = {"Id","Nome", "Idade", "CPF", "Endereço"};
		DefaultTableModel modeloTabela = new DefaultTableModel(colunas, 0);
		JTable tabela = new JTable(modeloTabela);

		// Preencher a tabela com os dados dos funcionários
		for (Funcionario funcionario : funcionarios) {
			modeloTabela.addRow(new Object[]{
					funcionario.getId(),
					funcionario.getNome(),
					funcionario.getIdade(),
					funcionario.getCpf(),
					funcionario.getEndereco()
			});
		}

		// Adicionar a tabela a um JScrollPane para rolagem
		JScrollPane scrollPane = new JScrollPane(tabela);
		tabela.setFillsViewportHeight(true);

		// Adicionando componentes ao painel
		panel.add(scrollPane, BorderLayout.CENTER);

		// Exibir o painel em um JOptionPane
		JOptionPane.showMessageDialog(null, panel, "Lista de Funcionários Cadastrados", JOptionPane.PLAIN_MESSAGE);
	}


	private static void atualizarFuncionario() throws SQLException {
		int idAtualizar = Integer.parseInt(JOptionPane.showInputDialog("ID do Funcionário a ser atualizado:"));
		Optional<Funcionario> funcionarioParaAtualizar = funcionarioService.buscarFuncionarioPorId(idAtualizar);
		if (funcionarioParaAtualizar.isPresent()) {
			Funcionario funcionario = funcionarioParaAtualizar.get();
			String novoNome = JOptionPane.showInputDialog("Novo Nome:", funcionario.getNome());
			int novaIdade = Integer
					.parseInt(JOptionPane.showInputDialog("Nova Idade:", funcionario.getIdade()));
			String novoEndereco = JOptionPane.showInputDialog("Novo Endereço:", funcionario.getEndereco());

			funcionario.setNome(novoNome);
			funcionario.setIdade(novaIdade);
			funcionario.setEndereco(novoEndereco);
			funcionarioService.atualizarFuncionario(funcionario);

			JOptionPane.showMessageDialog(null, "Funcionário atualizado com sucesso.");
		} else {
			JOptionPane.showMessageDialog(null, "Funcionário não encontrado.");
		}
	}

	private static void deletarFuncionario() throws SQLException {
		// Solicitar o ID do funcionário a ser removido
		int idRemover = Integer.parseInt(JOptionPane.showInputDialog("ID do Funcionário a ser removido:"));
		Optional<Funcionario> funcionarioParaRemover = funcionarioService.buscarFuncionarioPorId(idRemover);

		if (funcionarioParaRemover.isPresent()) {
			Funcionario funcionario = funcionarioParaRemover.get();

			// Criação do painel principal
			JPanel panel = new JPanel();
			panel.setLayout(new GridLayout(5, 2, 10, 10));
			panel.setBackground(new Color(226, 216, 194)); // Definindo a cor de fundo

			// Exibição dos dados do funcionário

			JLabel idLabel = new JLabel("ID:");
			JLabel idValue = new JLabel(String.valueOf(funcionario.getId()));

			JLabel nomeLabel = new JLabel("Nome:");
			JLabel nomeValue = new JLabel(funcionario.getNome());


			JLabel cpfLabel = new JLabel("Cpf:");
			JLabel cpfValue = new JLabel(funcionario.getCpf());


			JLabel enderecoLabel = new JLabel("Endereço:");
			JLabel enderecoValue = new JLabel(funcionario.getEndereco());

			// Adicionando componentes ao painel
			panel.add(nomeLabel);
			panel.add(nomeValue);
			panel.add(idLabel);
			panel.add(idValue);
			panel.add(cpfLabel);
			panel.add(cpfValue);
			panel.add(enderecoLabel);
			panel.add(enderecoValue);

			// Exibir o painel em um JOptionPane para confirmar exclusão
			int result = JOptionPane.showConfirmDialog(null, panel, "Excluir Funcionário", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

			if (result == JOptionPane.OK_OPTION) {
				// Confirmar a exclusão
				funcionarioService.removerFuncionario(idRemover);
				JOptionPane.showMessageDialog(null, "Funcionário removido com sucesso.");
			} else {
				JOptionPane.showMessageDialog(null, "Ação de exclusão cancelada.");
			}
		} else {
			JOptionPane.showMessageDialog(null, "Funcionário não encontrado.");
		}
	}


	// Gerenciamento de Adoções
	public static void gerenciarAdocoes() throws SQLException {
		String[] options = { "Realizar Adoção", "Listar Animais Disponíveis", "Ver Histórico de Adoções", "Voltar" };
		int choice = JOptionPane.showOptionDialog(null, "Escolha uma opção:", "Gerenciar Adoções",
				JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);

		switch (choice) {
		case 0:
			realizarAdocao();
			break;
		case 1:
			listarAnimaisDisponiveis();
			break;
		case 2:
			exibirHistoricoAdocoes();
			break;
		default:
			break;
		}
	}

	private static void realizarAdocao() throws SQLException {
		// Criação do painel principal
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(3, 2, 10, 10));

		// Campos de entrada
		JLabel idAnimalLabel = new JLabel("ID do Animal:");
		JTextField idAnimalField = new JTextField();

		JLabel cpfAdotanteLabel = new JLabel("CPF do Adotante:");
		JTextField cpfAdotanteField = new JTextField();

		JLabel idFuncionarioLabel = new JLabel("ID do Funcionário:");
		JTextField idFuncionarioField = new JTextField();

		// Adicionando componentes ao painel
		panel.add(idAnimalLabel);
		panel.add(idAnimalField);
		panel.add(cpfAdotanteLabel);
		panel.add(cpfAdotanteField);
		panel.add(idFuncionarioLabel);
		panel.add(idFuncionarioField);

		// Botão para confirmar a adoção
		int result = JOptionPane.showConfirmDialog(null, panel, "Realizar Adoção", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

		if (result == JOptionPane.OK_OPTION) {
			try {
				int idAnimal = Integer.parseInt(idAnimalField.getText());
				String cpfAdotante = cpfAdotanteField.getText();
				int idFuncionario = Integer.parseInt(idFuncionarioField.getText());

				// Buscar dados nos serviços correspondentes
				Optional<Animal> animal = animalService.buscarAnimalPorId(idAnimal);
				Optional<Adotante> adotante = adotanteService.buscarAdotantePorCpf(cpfAdotante);
				Optional<Funcionario> funcionario = funcionarioService.buscarFuncionarioPorId(idFuncionario);

				// Verificar se todos os dados necessários estão presentes
				if (animal.isPresent() && adotante.isPresent() && funcionario.isPresent()) {
					adocaoService.registrarAdocao(animal.get(), adotante.get(), funcionario.get());
					JOptionPane.showMessageDialog(null, "Adoção realizada com sucesso.");
				} else {
					JOptionPane.showMessageDialog(null, "Erro ao realizar adoção. Verifique os dados.");
				}
			} catch (NumberFormatException e) {
				JOptionPane.showMessageDialog(null, "Por favor, insira IDs válidos para Animal e Funcionário.");
			} catch (SQLException e) {
				JOptionPane.showMessageDialog(null, "Erro ao fazer a consulta no banco de dados.");
			}
		}
	}


	private static void listarAnimaisDisponiveis() throws SQLException {
		// Opções de filtro
		String[] options = { "Castrados", "Não Castrados", "Ambos" };
		int escolha = JOptionPane.showOptionDialog(null, "Escolha uma opção:", "Animais Disponíveis para Adoção",
				JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);

		// Variável para armazenar a lista de animais de acordo com a escolha do usuário
		List<Animal> animais;

		// Seleção da lista de animais conforme a opção escolhida
		switch (escolha) {
			case 0:
				animais = animalService.listarAnimaisCastrados(true);
				break;
			case 1:
				animais = animalService.listarAnimaisNaoCastrados(false);
				break;
			case 2:
				animais = animalService.listarAnimais();
				break;
			default:
				return; // Saída caso nenhuma opção válida seja selecionada
		}

		// Criação do painel principal
		JPanel panel = new JPanel(new BorderLayout());
		panel.setBackground(new Color(226, 216, 194)); // Definindo a cor de fundo

		// Configuração da tabela
		String[] colunas = { "ID", "Nome", "Espécie", "Raça", "Idade", "Castrado" };
		DefaultTableModel modeloTabela = new DefaultTableModel(colunas, 0);
		JTable tabela = new JTable(modeloTabela);

		// Preencher a tabela com os dados dos animais
		for (Animal animal : animais) {
			modeloTabela.addRow(new Object[]{
					animal.getId(),
					animal.getNome(),
					animal.getEspecie(),
					animal.getRaca(),
					animal.getIdade(),
					animal.isCastrado() ? "Sim" : "Não"
			});
		}

		// Adicionar a tabela a um JScrollPane para rolagem
		JScrollPane scrollPane = new JScrollPane(tabela);
		tabela.setFillsViewportHeight(true);

		// Adicionar componentes ao painel
		panel.add(scrollPane, BorderLayout.CENTER);

		// Exibir o painel em um JOptionPane
		JOptionPane.showMessageDialog(null, panel, "Lista de Animais Disponíveis para Adoção", JOptionPane.PLAIN_MESSAGE);
	}

	public static void exibirHistoricoAdocoes() throws SQLException {
		// Obter a lista de adoções do serviço
		List<Adocao> adocoes = adocaoService.listarHistoricoAdocoes();

		// Criação do painel principal
		JPanel panel = new JPanel(new BorderLayout());
		panel.setBackground(new Color(226, 216, 194)); // Definindo a cor de fundo

		// Configuração da tabela
		String[] colunas = { "Animal", "Adotante", "Funcionário", "Data da Adoção" };
		DefaultTableModel modeloTabela = new DefaultTableModel(colunas, 0);
		JTable tabela = new JTable(modeloTabela);

		// Preencher a tabela com os dados das adoções
		for (Adocao adocao : adocoes) {
			modeloTabela.addRow(new Object[]{
					adocao.getAnimal().getNome(),
					adocao.getAdotante().getNome(),
					adocao.getFuncionario().getNome(),
					adocao.getDataAdocao().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")),
			});
		}

		// Adicionar a tabela a um JScrollPane para rolagem
		JScrollPane scrollPane = new JScrollPane(tabela);
		tabela.setFillsViewportHeight(true);

		// Adicionando componentes ao painel
		panel.add(scrollPane, BorderLayout.CENTER);

		// Exibir o painel em um JOptionPane
		JOptionPane.showMessageDialog(null, panel, "Histórico de Adoções", JOptionPane.PLAIN_MESSAGE);
	}


}

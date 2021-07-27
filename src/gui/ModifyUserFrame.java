package gui;

import dao.UserSQL;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * ����Ա�޸��û�����
 */
public class ModifyUserFrame extends JFrame
{
    public static void main(String[] args)
    {
        new ModifyUserFrame("340103200107111026");
    }

    public static final int DEFAULT_WIDTH = 400;
    public static final int DEFAULT_HEIGHT = 600;
    public static final String[] columnNames = {"���֤��", "����", "�˳��˳�Ʊ��"};
    private JTable passengerTable;
    private JPanel northPanel;
    private JPanel southPanel;
    private JLabel idLabel;
    private JLabel nameLabel;
    private JLabel passwordLabel;
    private JTextField idField;
    private JTextField nameField;
    private JTextField passwordField;
    private JButton modifyButton;
    private JButton addButton;
    private JButton deleteButton;
    private JButton returnButton;
    private String userId;

    public ModifyUserFrame(String userId)
    {
        this.userId = userId;
        setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
        setLocationRelativeTo(null);                //������ʾ
        setResizable(false);
        setLayout(null);

        setTitle("�޸��û�");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        addButton = new JButton("���ӳ˳���");
        addButton.addActionListener(new AddAction());
        deleteButton = new JButton("ɾ���˳���");
        deleteButton.addActionListener(new DeleteAction());
        returnButton = new JButton("������һ��");
        returnButton.addActionListener(new ReturnAction());

        northPanel = new JPanel();
        northPanel.setBounds(0, 0, DEFAULT_WIDTH, 200);
        northPanel.setLayout(null);

        idLabel = new JLabel("���֤��");
        idLabel.setBounds(70, 30, 80, 30);
        nameLabel = new JLabel("����");
        nameLabel.setBounds(70, 70, 80, 30);
        passwordLabel = new JLabel("����");
        passwordLabel.setBounds(70, 110, 80, 30);
        idField = new JTextField();
        idField.setBounds(150, 30, 150, 30);
        idField.setText(userId);
        idField.setEditable(false);
        nameField = new JTextField();
        nameField.setBounds(150, 70, 150, 30);
        nameField.setText(UserSQL.queryUserName(userId));
        nameField.setEditable(false);
        passwordField = new JTextField();
        passwordField.setBounds(150, 110, 150, 30);
        passwordField.setText(UserSQL.queryUserPwd(userId));

        modifyButton = new JButton("�޸�");
        modifyButton.setBounds(160, 160, 80, 30);
        modifyButton.addActionListener(new ModifyAction());

        northPanel.add(idLabel);
        northPanel.add(idField);
        northPanel.add(nameLabel);
        northPanel.add(nameField);
        northPanel.add(passwordLabel);
        northPanel.add(passwordField);
        northPanel.add(modifyButton);

        passengerTable = new JTable(UserSQL.queryPassInfo(userId), columnNames);
        JScrollPane pane = new JScrollPane(passengerTable);
        pane.setBounds(0, 210, DEFAULT_WIDTH, 310);
        passengerTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        southPanel = new JPanel();
        southPanel.setBounds(0, 525, DEFAULT_WIDTH, 90);
        southPanel.add(addButton);
        southPanel.add(deleteButton);
        southPanel.add(returnButton);

        add(northPanel);
        add(pane);
        add(southPanel);

        setVisible(true);
    }

    public void updateTable()
    {
        DefaultTableModel model = new DefaultTableModel();
        model.setColumnIdentifiers(columnNames);
        Object[][] info = UserSQL.queryPassInfo(userId);
        for (int i = 0; i < info.length; i++)
            model.addRow(info[i]);
        passengerTable.setModel(model);
    }

    private class ModifyAction implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {
            String password = passwordField.getText().trim();
            if (password.equals(""))
            {
                JOptionPane.showMessageDialog(ModifyUserFrame.this, "�������޸ĺ�����룡", "����", JOptionPane.WARNING_MESSAGE);
                return;
            }
            UserSQL.updateUserPwd(userId, password);
            JOptionPane.showMessageDialog(ModifyUserFrame.this, "�޸ĳɹ�!");
        }
    }

    private class AddAction implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {
            new AddPassengerFrame(userId, 1);
            dispose();
        }
    }

    private class DeleteAction implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {
            int i = ModifyUserFrame.this.passengerTable.getSelectedRow();
            if (i == -1)
            {
                JOptionPane.showMessageDialog(ModifyUserFrame.this, "��ѡ��Ҫɾ���ĳ˳�����Ϣ��", "����", JOptionPane.WARNING_MESSAGE);
                return;
            }
            String id = ModifyUserFrame.this.passengerTable.getValueAt(i, 0).toString();
            if (userId.equals(id))
            {
                JOptionPane.showMessageDialog(ModifyUserFrame.this, "����ɾ������", "����", JOptionPane.WARNING_MESSAGE);
                return;
            }
            UserSQL.deletePassenger(userId, id);
            JOptionPane.showMessageDialog(ModifyUserFrame.this, "ɾ���ɹ�!");
            updateTable();
        }
    }

    private class ReturnAction implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {
            dispose();
            new AdmUserFrame();
        }
    }
}

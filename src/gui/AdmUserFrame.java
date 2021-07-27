package gui;

import dao.StationSQL;
import dao.UserSQL;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * ����Ա�����û�����
 */
public class AdmUserFrame extends JFrame
{
    public static void main(String[] args)
    {
        new AdmUserFrame();
    }

    public static final int DEFAULT_WIDTH = 400;
    public static final int DEFAULT_HEIGHT = 600;
    public static final String[] columnNames = {"���֤��", "����", "����", "��Ʊ��", "�˳�����"};
    private JTable userTable;
    private JPanel northPanel;
    private JPanel southPanel;
    private JTextField field;
    private JButton queryButton;
    private JButton addButton;
    private JButton deleteButton;
    private JButton modifyButton;
    private JButton returnButton;

    public AdmUserFrame()
    {
        setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
        setLocationRelativeTo(null);                //������ʾ
        setResizable(false);

        setTitle("�û�����");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        field = new JTextField(12);
        queryButton = new JButton("����");
        queryButton.addActionListener(new QueryAction());
        northPanel = new JPanel();
        northPanel.add(field);
        northPanel.add(queryButton);

        userTable = new JTable(UserSQL.queryUser(), columnNames);
        add(new JScrollPane(userTable), BorderLayout.CENTER);
        userTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        addButton = new JButton("�����û�");
        addButton.addActionListener(new AddAction());
        deleteButton = new JButton("ɾ���û�");
        deleteButton.addActionListener(new DeleteAction());
        modifyButton = new JButton("�޸��û�");
        modifyButton.addActionListener(new ModifyAction());
        returnButton = new JButton("������һ��");
        returnButton.addActionListener(new ReturnAction());

        southPanel = new JPanel();
        southPanel.add(addButton);
        southPanel.add(deleteButton);
        southPanel.add(modifyButton);
        southPanel.add(returnButton);

        add(northPanel, BorderLayout.NORTH);
        add(southPanel, BorderLayout.SOUTH);

        setVisible(true);
    }

    public void updateTable()
    {
        DefaultTableModel model = new DefaultTableModel();
        model.setColumnIdentifiers(columnNames);
        Object[][] info = UserSQL.queryUser();
        for (int i = 0; i < info.length; i++)
            model.addRow(info[i]);
        userTable.setModel(model);
    }

    private class QueryAction implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {
            String userId = field.getText().trim();
            if (userId.equals(""))
            {
                JOptionPane.showMessageDialog(AdmUserFrame.this, "������Ҫ�����û������֤�ţ�", "����", JOptionPane.WARNING_MESSAGE);
                return;
            }
            if (!AddPassengerFrame.validID(userId))
            {
                JOptionPane.showMessageDialog(AdmUserFrame.this, "��������Ч�����֤�ţ�", "����", JOptionPane.WARNING_MESSAGE);
                return;
            }
            if (!UserSQL.isUserExisting(userId))
            {
                JOptionPane.showMessageDialog(AdmUserFrame.this, "���û������ڣ�", "����", JOptionPane.WARNING_MESSAGE);
                return;
            }
            new ModifyUserFrame(userId);
            dispose();
        }
    }

    private class AddAction implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {
            new AddUserFrame();
            dispose();
        }
    }

    private class DeleteAction implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {
            int i = AdmUserFrame.this.userTable.getSelectedRow();
            if (i == -1)
            {
                JOptionPane.showMessageDialog(AdmUserFrame.this, "��ѡ��Ҫɾ�����û���Ϣ��", "����", JOptionPane.WARNING_MESSAGE);
                return;
            }
            String userId = AdmUserFrame.this.userTable.getValueAt(i, 0).toString();
            UserSQL.deleteUser(userId);
            JOptionPane.showMessageDialog(AdmUserFrame.this, "ɾ���ɹ�!");
            updateTable();
        }
    }

    private class ModifyAction implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {
            int i = AdmUserFrame.this.userTable.getSelectedRow();
            if (i == -1)
            {
                JOptionPane.showMessageDialog(AdmUserFrame.this, "��ѡ��Ҫ�޸ĵ��û���Ϣ��", "����", JOptionPane.WARNING_MESSAGE);
                return;
            }
            String userId = AdmUserFrame.this.userTable.getValueAt(i, 0).toString();
            new ModifyUserFrame(userId);
            dispose();
        }
    }

    private class ReturnAction implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {
            dispose();
            new AdminFrame();
        }
    }
}

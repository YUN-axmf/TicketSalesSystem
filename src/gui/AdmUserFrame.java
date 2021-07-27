package gui;

import dao.StationSQL;
import dao.UserSQL;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * 管理员管理用户界面
 */
public class AdmUserFrame extends JFrame
{
    public static void main(String[] args)
    {
        new AdmUserFrame();
    }

    public static final int DEFAULT_WIDTH = 400;
    public static final int DEFAULT_HEIGHT = 600;
    public static final String[] columnNames = {"身份证号", "姓名", "密码", "购票数", "乘车人数"};
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
        setLocationRelativeTo(null);                //居中显示
        setResizable(false);

        setTitle("用户管理");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        field = new JTextField(12);
        queryButton = new JButton("查找");
        queryButton.addActionListener(new QueryAction());
        northPanel = new JPanel();
        northPanel.add(field);
        northPanel.add(queryButton);

        userTable = new JTable(UserSQL.queryUser(), columnNames);
        add(new JScrollPane(userTable), BorderLayout.CENTER);
        userTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        addButton = new JButton("增加用户");
        addButton.addActionListener(new AddAction());
        deleteButton = new JButton("删除用户");
        deleteButton.addActionListener(new DeleteAction());
        modifyButton = new JButton("修改用户");
        modifyButton.addActionListener(new ModifyAction());
        returnButton = new JButton("返回上一级");
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
                JOptionPane.showMessageDialog(AdmUserFrame.this, "请输入要查找用户的身份证号！", "错误", JOptionPane.WARNING_MESSAGE);
                return;
            }
            if (!AddPassengerFrame.validID(userId))
            {
                JOptionPane.showMessageDialog(AdmUserFrame.this, "请输入有效的身份证号！", "错误", JOptionPane.WARNING_MESSAGE);
                return;
            }
            if (!UserSQL.isUserExisting(userId))
            {
                JOptionPane.showMessageDialog(AdmUserFrame.this, "该用户不存在！", "错误", JOptionPane.WARNING_MESSAGE);
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
                JOptionPane.showMessageDialog(AdmUserFrame.this, "请选择要删除的用户信息！", "错误", JOptionPane.WARNING_MESSAGE);
                return;
            }
            String userId = AdmUserFrame.this.userTable.getValueAt(i, 0).toString();
            UserSQL.deleteUser(userId);
            JOptionPane.showMessageDialog(AdmUserFrame.this, "删除成功!");
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
                JOptionPane.showMessageDialog(AdmUserFrame.this, "请选择要修改的用户信息！", "错误", JOptionPane.WARNING_MESSAGE);
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

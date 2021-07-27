package gui;

import dao.UserSQL;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * 管理员修改用户界面
 */
public class ModifyUserFrame extends JFrame
{
    public static void main(String[] args)
    {
        new ModifyUserFrame("340103200107111026");
    }

    public static final int DEFAULT_WIDTH = 400;
    public static final int DEFAULT_HEIGHT = 600;
    public static final String[] columnNames = {"身份证号", "姓名", "乘车人车票数"};
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
        setLocationRelativeTo(null);                //居中显示
        setResizable(false);
        setLayout(null);

        setTitle("修改用户");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        addButton = new JButton("增加乘车人");
        addButton.addActionListener(new AddAction());
        deleteButton = new JButton("删除乘车人");
        deleteButton.addActionListener(new DeleteAction());
        returnButton = new JButton("返回上一级");
        returnButton.addActionListener(new ReturnAction());

        northPanel = new JPanel();
        northPanel.setBounds(0, 0, DEFAULT_WIDTH, 200);
        northPanel.setLayout(null);

        idLabel = new JLabel("身份证号");
        idLabel.setBounds(70, 30, 80, 30);
        nameLabel = new JLabel("姓名");
        nameLabel.setBounds(70, 70, 80, 30);
        passwordLabel = new JLabel("密码");
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

        modifyButton = new JButton("修改");
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
                JOptionPane.showMessageDialog(ModifyUserFrame.this, "请输入修改后的密码！", "错误", JOptionPane.WARNING_MESSAGE);
                return;
            }
            UserSQL.updateUserPwd(userId, password);
            JOptionPane.showMessageDialog(ModifyUserFrame.this, "修改成功!");
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
                JOptionPane.showMessageDialog(ModifyUserFrame.this, "请选择要删除的乘车人信息！", "错误", JOptionPane.WARNING_MESSAGE);
                return;
            }
            String id = ModifyUserFrame.this.passengerTable.getValueAt(i, 0).toString();
            if (userId.equals(id))
            {
                JOptionPane.showMessageDialog(ModifyUserFrame.this, "不可删除自身！", "错误", JOptionPane.WARNING_MESSAGE);
                return;
            }
            UserSQL.deletePassenger(userId, id);
            JOptionPane.showMessageDialog(ModifyUserFrame.this, "删除成功!");
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

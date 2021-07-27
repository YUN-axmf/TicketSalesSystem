package gui;

import dao.TicketSQL;
import dao.TrainSQL;
import dao.UserSQL;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

/**
 * ���ӳ�Ʊ��Ϣ������
 * @author LiYueyun
 */
public class ModifyTicketFrame extends JFrame
{
    public static void main(String[] args)
    {
        new ModifyTicketFrame("ZH000000001");
    }

    public static final int DEFAULT_WIDTH = 400;
    public static final int DEFAULT_HEIGHT = 650;
    private JLabel ticNoLabel;
    private JLabel faresLabel;
    private JLabel triNoLabel;
    private JLabel dateLabel;
    private JLabel statusLabel;
    private JLabel passIdLabel;
    private JLabel purchaserIdLabel;
    private JLabel coachLabel;
    private JLabel seatLabel;
    private JLabel classLabel;
    private JLabel oriStaLabel;
    private JLabel destinationLabel;
    private JTextField oriStaField;
    private JTextField destinationField;
    private JTextField ticNoField;
    private JTextField faresField;
    private JTextField triNoField;
    private JTextField dateField;
    private JComboBox<String> statusCombo;
    private JComboBox<String> passIdCombo;
    private JComboBox<String> purchaserIdCombo;
    private JComboBox<String> coachCombo;
    private JComboBox<String> seatCombo;
    private JTextField classField;
    private JButton modifyButton;
    private JButton returnButton;
    private String ticNo;
    private String date;

    public ModifyTicketFrame(String ticNo)
    {
        this.ticNo = ticNo;
        Object[] ticInfo = TicketSQL.queryTicInfo(ticNo);
        this.date = ticInfo[4].toString();

        setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
        setLocationRelativeTo(null);                //������ʾ
        setLayout(null);
        setResizable(false);

        setTitle("�޸ĳ�Ʊ");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        ticNoLabel = new JLabel("��Ʊ���");
        ticNoLabel.setBounds(70, 40, 70, 30);
        ticNoField = new JTextField(ticNo);
        ticNoField.setEditable(false);
        ticNoField.setBounds(180, 40, 160, 30);

        faresLabel = new JLabel("Ʊ��");
        faresLabel.setBounds(70, 90, 70, 30);
        faresField = new JTextField(ticInfo[1].toString());
        faresField.setBounds(180, 90, 160, 30);

        triNoLabel = new JLabel("���κ�");
        triNoLabel.setBounds(70, 140, 70, 30);
        triNoField = new JTextField(ticInfo[6].toString());
        triNoField.setBounds(180, 140, 160, 30);
        triNoField.setEditable(false);

        oriStaLabel = new JLabel("����վ");
        oriStaLabel.setBounds(70, 190, 50, 30);
        oriStaField = new JTextField(ticInfo[2].toString());
        oriStaField.setBounds(120, 190, 70, 30);
        oriStaField.setEditable(false);

        destinationLabel = new JLabel("�յ�վ");
        destinationLabel.setBounds(220, 190, 50, 30);
        destinationField = new JTextField(ticInfo[3].toString());
        destinationField.setBounds(270, 190, 70, 30);
        destinationField.setEditable(false);

        dateLabel = new JLabel("��������");
        dateLabel.setBounds(70, 240, 70, 30);
        dateField = new JTextField();
        dateField.setBounds(180, 240, 160, 30);
        dateField.setText(date);
        dateField.setEditable(false);

        coachLabel = new JLabel("�����");
        coachLabel.setBounds(70, 290, 50, 30);
        coachCombo = new JComboBox<>();
        coachCombo.setBounds(120, 290, 70, 30);
        String[] coachNo = TrainSQL.queryCoach();
        coachCombo.addItem("");
        for (int i = 0; i < coachNo.length; i++)
            coachCombo.addItem(coachNo[i]);
        coachCombo.setSelectedItem(ticInfo[8]);

        seatLabel = new JLabel("��λ��");
        seatLabel.setBounds(220, 290, 50, 30);
        seatCombo = new JComboBox<>();
        seatCombo.setBounds(270, 290, 70, 30);
        String[] seatNo = TrainSQL.querySeat();
        seatCombo.addItem("");
        for (int i = 0; i < seatNo.length; i++)
            seatCombo.addItem(seatNo[i]);
        seatCombo.setSelectedItem(ticInfo[9]);

        classLabel = new JLabel("��λ�ȼ�");
        classLabel.setBounds(70, 340, 70, 30);
        classField = new JTextField(ticInfo[10].toString());
        classField.setEnabled(false);
        classField.setBounds(180, 340, 160, 30);

        statusLabel = new JLabel("��Ʊ״̬");
        statusLabel.setBounds(70, 390, 70, 30);
        statusCombo = new JComboBox<>();
        statusCombo.setBounds(180, 390, 160, 30);
        statusCombo.addItem("δ��");
        statusCombo.addItem("����");
        if (TicketSQL.isSold(ticNo))
            statusCombo.setSelectedItem("����");
        else
            statusCombo.setSelectedItem("δ��");

        passIdLabel = new JLabel("�˳������֤��");
        passIdLabel.setBounds(70, 440, 100, 30);
        passIdCombo = new JComboBox<>();
        passIdCombo.setBounds(180, 440, 160, 30);
        passIdCombo.addItem("");
        String[] passId = UserSQL.queryPassengerId();
        for (int i = 0; i < passId.length; i++)
            passIdCombo.addItem(passId[i]);
        passIdCombo.setSelectedItem(ticInfo[5]);

        purchaserIdLabel = new JLabel("��Ʊ�����֤��");
        purchaserIdLabel.setBounds(70, 490, 100, 30);
        purchaserIdCombo = new JComboBox<>();
        purchaserIdCombo.setBounds(180, 490, 160, 30);
        purchaserIdCombo.addItem("");
        String[] userId = UserSQL.queryUserId();
        for (int i = 0; i < userId.length; i++)
            purchaserIdCombo.addItem(userId[i]);
        purchaserIdCombo.setSelectedItem(TicketSQL.queryPurchaser(ticNo));

        modifyButton = new JButton("�޸ĳ�Ʊ");
        modifyButton.setBounds(80, 560, 100, 30);
        modifyButton.addActionListener(new ModifyAction());

        returnButton = new JButton("������һ��");
        returnButton.setBounds(220, 560, 100, 30);
        returnButton.addActionListener(new ReturnAction());

        coachCombo.addItemListener(new ItemListener()
        {
            @Override
            public void itemStateChanged(ItemEvent e)
            {
                String coach = coachCombo.getItemAt(coachCombo.getSelectedIndex());
                String seat = seatCombo.getItemAt(seatCombo.getSelectedIndex());
                if (!coach.equals("") && !seat.equals(""))
                    classField.setText(TicketSQL.querySeatClass(coach, seat));
                if (coach.equals("") && seat.equals(""))
                    classField.setText("����");
                if (classField.getText().equals("") || (coach.equals("") && !seat.equals("")) || (!coach.equals("") && seat.equals("")))
                    classField.setText("�޴���λ");
            }
        });
        seatCombo.addItemListener(new ItemListener()
        {
            @Override
            public void itemStateChanged(ItemEvent e)
            {
                String coach = coachCombo.getItemAt(coachCombo.getSelectedIndex());
                String seat = seatCombo.getItemAt(seatCombo.getSelectedIndex());
                if (!coach.equals("") && !seat.equals(""))
                    classField.setText(TicketSQL.querySeatClass(coach, seat));
                if (coach.equals("") && seat.equals(""))
                    classField.setText("����");
                if (classField.getText().equals("") || (coach.equals("") && !seat.equals("")) || (!coach.equals("") && seat.equals("")))
                    classField.setText("�޴���λ");
            }
        });

        add(ticNoLabel);
        add(faresLabel);
        add(triNoLabel);
        add(dateLabel);
        add(statusLabel);
        add(passIdLabel);
        add(purchaserIdLabel);
        add(coachLabel);
        add(seatLabel);
        add(classLabel);
        add(ticNoField);
        add(triNoField);
        add(faresField);
        add(triNoField);
        add(dateField);
        add(statusCombo);
        add(passIdCombo);
        add(purchaserIdCombo);
        add(coachCombo);
        add(seatCombo);
        add(classField);
        add(modifyButton);
        add(returnButton);
        add(oriStaLabel);
        add(oriStaField);
        add(destinationLabel);
        add(destinationField);

        setVisible(true);
    }

    private class ModifyAction implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {
            String triNo = ticNoField.getText();
            String fares = faresField.getText().trim();
            String coach = coachCombo.getSelectedItem().toString();
            String seat = seatCombo.getSelectedItem().toString();
            String seatClass = classField.getText();
            String status = statusCombo.getSelectedItem().toString();
            if (fares.equals(""))
            {
                JOptionPane.showMessageDialog(ModifyTicketFrame.this, "������Ʊ�ۣ�", "����", JOptionPane.WARNING_MESSAGE);
                return;
            }
            if (!AddTicketFrame.validFares(fares))
            {
                JOptionPane.showMessageDialog(ModifyTicketFrame.this, "��������Ч��Ʊ�ۣ�", "����", JOptionPane.WARNING_MESSAGE);
                return;
            }
            if (classField.getText().equals("�޴���λ"))
            {
                JOptionPane.showMessageDialog(ModifyTicketFrame.this, "������ѡ����λ��", "����", JOptionPane.WARNING_MESSAGE);
                return;
            }
            if (status.equals("δ��"))
            {
                if (seatClass.equals("����"))
                {
                    TicketSQL.updateTicket(ticNo, Double.parseDouble(fares));
                }
                else
                {
                    if (!TicketSQL.validSeat(triNo, date, Integer.parseInt(coach), seat))
                    {
                        JOptionPane.showMessageDialog(ModifyTicketFrame.this, "����λ�Ѵ��ڳ�Ʊ��", "����", JOptionPane.WARNING_MESSAGE);
                        return;
                    }
                    TicketSQL.updateTicket(ticNo, Double.parseDouble(fares), Integer.parseInt(coach), seat, seatClass);
                }
                JOptionPane.showMessageDialog(ModifyTicketFrame.this, "�޸ĳɹ�!");
                dispose();
                new AdmTicketFrame();
            }
            else if (status.equals("����"))
            {
                String passId = passIdCombo.getSelectedItem().toString();
                String purchaserId = purchaserIdCombo.getSelectedItem().toString();
                if (passId.equals("") || purchaserId.equals(""))
                {
                    JOptionPane.showMessageDialog(ModifyTicketFrame.this, "��ѡ��˳��˺͹�Ʊ�ˣ�", "����", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                if (seatClass.equals("����"))
                    TicketSQL.updateTicket(ticNo, Double.parseDouble(fares), passId, purchaserId);
                else
                {
                    if (!TicketSQL.validSeat(triNo, date, Integer.parseInt(coach), seat))
                    {
                        JOptionPane.showMessageDialog(ModifyTicketFrame.this, "����λ�Ѵ��ڳ�Ʊ��", "����", JOptionPane.WARNING_MESSAGE);
                        return;
                    }
                    TicketSQL.updateTicket(ticNo, Double.parseDouble(fares), Integer.parseInt(coach), seat, seatClass, passId, purchaserId);
                }
                JOptionPane.showMessageDialog(ModifyTicketFrame.this, "�޸ĳɹ�!");
                dispose();
                new AdmTicketFrame();
            }
        }
    }

    private class ReturnAction implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {
            dispose();
            new AdmTicketFrame();
        }
    }

}


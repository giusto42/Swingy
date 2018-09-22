package com.swingy.db;

import com.swingy.herotype.HeroClass;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class DbHandler {

    public DbHandler() {
    }

    private Connection SQLConnect() {

        Connection c = null;

        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:src/main/res/heroState.db");
            createTable(c);
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        return c;
    }

    private void createTable(Connection con) {

        Statement stmt = null;

        try {
            stmt = con.createStatement();
            String sql = "CREATE TABLE IF NOT EXISTS HEROES " +
                    "(ID INT PRIMARY KEY               NOT NULL," +
                    " NAME              VARCHAR(255)   NOT NULL, " +
                    " TYPE              VARCHAR(255)   NOT NULL, " +
                    " LEVEL             INT            NOT NULL, " +
                    " EXPERIENCE        INT            NOT NULL, " +
                    " ATTACK            DOUBLE         NOT NULL, " +
                    " ARMOR             DOUBLE         NOT NULL, " +
                    " HITPOINT          DOUBLE         NOT NULL, " +
                    " ARTEFACT          VARCHAR(255)   NOT NULL)";
            stmt.executeUpdate(sql);
            stmt.close();
        } catch (Exception e) {
            //System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
    }

    public void insertDateInTable(int id, String name, String type, int level, int experience, double attack, double armor, double hitPoint, String artefact) {

        String values = "VALUES (" + id + ", '" + name + "', '" + type + "', " + level + ", " + experience + ", " + attack + ", " + armor + ", " + hitPoint + ", '" + artefact + "');";
        Connection con = SQLConnect();

        Statement stmt = null;

        try {
            stmt = con.createStatement();
            String sql = "INSERT INTO HEROES (ID,NAME,TYPE,LEVEL,EXPERIENCE,ATTACK,ARMOR,HITPOINT,ARTEFACT) " + values;
            stmt.executeUpdate(sql);

            stmt.close();
            con.commit();
            con.close();
        } catch (Exception e) {
            //System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
    }

    public ArrayList<HeroClass> readData() {

        Connection con = SQLConnect();
        Statement stmt = null;
        ArrayList<HeroClass> heroList = new ArrayList<HeroClass>();

        try {
            stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM HEROES;");

            while (rs.next()) {
                HeroClass currentHero = new HeroClass();
                String name = rs.getString("name");
                String type = rs.getString("type");
                int level = rs.getInt("level");
                int experience = rs.getInt("experience");
                double attack = rs.getDouble("attack");
                double armor = rs.getDouble("armor");
                double hitPoint = rs.getDouble("hitpoint");
                String artefact = rs.getString("artefact");

                currentHero.setName(name);
                currentHero.setType(type);
                currentHero.setLevel(level);
                currentHero.setExperience(experience);
                currentHero.setAttack(attack);
                currentHero.setArmor(armor);
                currentHero.setHitPoint(hitPoint);
                currentHero.setArtefact(artefact);
                heroList.add(currentHero);
            }
            rs.close();
            stmt.close();
            con.close();
        } catch (Exception e) {
            //System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
        return heroList;
    }

    public void updateData(int id, int level, int experience) {

        Connection con = SQLConnect();
        Statement stmt = null;

        try {
            stmt = con.createStatement();
            String sql = "UPDATE HEROES set LEVEL = " + level + ", EXPERIENCE = " + experience + " where ID=" + id + ";";
            stmt.executeUpdate(sql);

            con.commit();
            stmt.close();
            con.close();
        } catch (Exception e) {
            //System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
    }

    public void updateData(int id, String artefact, Double atribute) {

        Connection con = SQLConnect();
        Statement stmt = null;

        try {
            String sql;
            stmt = con.createStatement();
            switch (artefact) {
                case "weapon":
                    sql = "UPDATE HEROES set ARTEFACT = '" + artefact + "', ATTACK = " + atribute + " where ID=" + id + ";";
                    break;
                case "armor":
                    sql = "UPDATE HEROES set ARTEFACT = '" + artefact + "', ARMOR = " + atribute + " where ID=" + id + ";";
                    break;
                case "helmet":
                    sql = "UPDATE HEROES set ARTEFACT = '" + artefact + "', HITPOINT = " + atribute + " where ID=" + id + ";";
                    break;
                default:
                    sql = "UPDATE HEROES set ARTEFACT = '" + artefact + "' where ID=" + id + ";";
                    break;
            }
            stmt.executeUpdate(sql);

            con.commit();
            stmt.close();
            con.close();
        } catch (Exception e) {
            //System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
    }

}


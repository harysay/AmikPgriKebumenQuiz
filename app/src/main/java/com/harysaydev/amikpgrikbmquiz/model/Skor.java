package com.harysaydev.amikpgrikbmquiz.model;

/**
 * Created by harysay on 17/12/2017.
 */

public class Skor {
    private String Jeneng;
    private String JumlahPoint;
    private String GrafikaKomputer;
    private String Pbo;
    private String KecerdasanBuatan;
    private String Rpl;

    public Skor(String jeneng,String nilaiGrafikakomp,String nilaiPbo,String nilaiKecerdasan,String nilaiRpl, String jumlahPoint) {
        Jeneng = jeneng;
        GrafikaKomputer = nilaiGrafikakomp;
        Pbo = nilaiPbo;
        KecerdasanBuatan = nilaiKecerdasan;
        Rpl = nilaiRpl;
        JumlahPoint = jumlahPoint;
    }
    public Skor() {
    }

    public String getJeneng() {
        return Jeneng;
    }

    public void setJeneng(String jeneng) {
        Jeneng = jeneng;
    }

    public String getNilaiGrafika() {
        return GrafikaKomputer;
    }

    public void setNilaiGrafika(String nilaiGraf) {
        GrafikaKomputer = nilaiGraf;
    }

    public String getNilaiPbo() {
        return Pbo;
    }

    public void setNilaiPbo(String nilaiPbo) {
        Pbo = nilaiPbo;
    }

    public String getKecerdasanBuatan() {
        return KecerdasanBuatan;
    }

    public void setKecerdasanBuatan(String kecerdasanBuatan) {
        KecerdasanBuatan = kecerdasanBuatan;
    }

    public String getRpl() {
        return Rpl;
    }

    public void setRpl(String rpl) {
        Rpl = rpl;
    }

    public String getJumlahPoint() {
        return JumlahPoint;
    }

    public void setJumlahPoint(String jumlahPoint) {
        JumlahPoint = jumlahPoint;
    }



}

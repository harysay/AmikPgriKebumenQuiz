package com.harysaydev.amikpgrikbmquiz.model;

/**
 * Created by harysay on 17/12/2017.
 */

public class ResultPerTest {
    public String ResultJeneng;
    public String ResultGrafikaKomputer;
    public String ResultPbo;
    public String ResultKecerdasanBuatan;
    public String ResultRpl;

//    public ResultPerTest(String resjeneng, String nilaiGrafikakomp, String nilaiPbo, String nilaiKecerdasan, String nilaiRpl) {
//        ResultJeneng = resjeneng;
//        ResultGrafikaKomputer = nilaiGrafikakomp;
//        ResultPbo = nilaiPbo;
//        ResultKecerdasanBuatan = nilaiKecerdasan;
//        ResultRpl = nilaiRpl;
//    }
//    public ResultPerTest(String resultjeneng, String resultGrafikakomp) {
//        ResultJeneng = resultjeneng;
//        ResultGrafikaKomputer = resultGrafikakomp;
//    }
    public ResultPerTest() {
    }

    public String getJeneng() {
        return ResultJeneng;
    }

    public void setJeneng(String jeneng) {
        ResultJeneng = jeneng;
    }

    public String getNilaiGrafika() {
        return ResultGrafikaKomputer;
    }

    public void setNilaiGrafika(String nilaiGraf) {
        ResultGrafikaKomputer = nilaiGraf;
    }

    public String getNilaiPbo() {
        return ResultPbo;
    }

    public void setNilaiPbo(String nilaiPbo) {
        ResultPbo = nilaiPbo;
    }

    public String getKecerdasanBuatan() {
        return ResultKecerdasanBuatan;
    }

    public void setKecerdasanBuatan(String nama, String kecerdasanBuatan) {
        ResultJeneng = nama;
        ResultKecerdasanBuatan = kecerdasanBuatan;
    }

    public String getRpl() {
        return ResultRpl;
    }

    public void setRpl(String nama,String rpl) {
        ResultJeneng = nama;
        ResultRpl = rpl;
    }



}

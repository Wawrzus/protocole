package com.example.protocole;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import com.github.gcacace.signaturepad.views.SignaturePad;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.TextAlignment;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        builder.detectFileUriExposure();
    }

    public void sendMessage(View view) throws Exception {
        createPdf();
    }

    public void createPdf() throws Exception {
        float fontSize = 10, tableFontSize = 8;
        float margin = 30, noMargin = 0;

        try {
            //Tworzenie elementów w pliku pdf
            EditText dataProtokoluTV = (EditText) findViewById(R.id.DataProtokoluEditText);
            String dataProtokoluStr = dataProtokoluTV.getText().toString();
            Paragraph dataParagraph = CreateEmail.createParagraph("Data ".concat(dataProtokoluStr), fontSize, noMargin);
            dataParagraph.setTextAlignment(TextAlignment.RIGHT);

            EditText numerProtokoluTV = (EditText) findViewById(R.id.NumerProtokoluEditText);
            String numerProtokoluStr = numerProtokoluTV.getText().toString();
            Paragraph pageHeadline = CreateEmail.createHeadline("PROTOKÓŁ USŁUGI SERWISOWEJ nr PG/".concat(numerProtokoluStr), 18, noMargin);
            pageHeadline.setTextAlignment(TextAlignment.CENTER);

            EditText kopalniaTV = (EditText) findViewById(R.id.KopalniaEditText);
            String kopalniaStr = kopalniaTV.getText().toString();
            Paragraph kopalnia = CreateEmail.createParagraph("1. Kopalnia: ".concat(kopalniaStr), fontSize, noMargin);

            Paragraph urzadzenie = CreateEmail.createParagraph("2. Urządzenie:", fontSize, noMargin);
            EditText typNumerTV = (EditText)findViewById(R.id.TypEditText);
            String typNumerStr = typNumerTV.getText().toString();
            Paragraph typNumer = CreateEmail.createParagraph("a) typ i numer: ".concat(typNumerStr), fontSize, margin);

            EditText miejsceTV = (EditText)findViewById(R.id.MiejsceEditText);
            String miejsceStr = miejsceTV.getText().toString();
            Paragraph zabudowa = CreateEmail.createParagraph("3. Miejsce zabudowy: ".concat(miejsceStr), fontSize, noMargin);

            EditText zgloszenieTV = (EditText)findViewById(R.id.ZgloszenieEditText);
            String zgloszenieStr = zgloszenieTV.getText().toString();
            Paragraph zgloszenie = CreateEmail.createParagraph("4. Zgłoszenie przez kopalnie: ".concat(zgloszenieStr), fontSize, noMargin);
            EditText osobaTV = (EditText)findViewById(R.id.ZgloszenieEditText);
            String osobaStr = osobaTV.getText().toString();
            Paragraph osoba = CreateEmail.createParagraph("a) osoba zgłaszająca: ".concat(osobaStr), fontSize, margin);
            EditText dataPrzyczynaTV = (EditText)findViewById(R.id.DataEditText);
            String dataPrzyczynaStr = dataPrzyczynaTV.getText().toString();
            Paragraph dataPrzyczyna = CreateEmail.createParagraph("b) data i przyczyna zgłoszenia: ".concat(dataPrzyczynaStr), fontSize, margin);

            EditText uszkodzeniaTV = (EditText)findViewById(R.id.UszkodzeniaEditText);
            String uszkodzeniaStr = uszkodzeniaTV.getText().toString();
            Paragraph uszkodzenia = CreateEmail.createParagraph("5. Uszkodzenia urzadzenia stwierdzone przez pracowników serwisu: ".concat(uszkodzeniaStr), fontSize, noMargin);

            EditText czynnosciTV = (EditText)findViewById(R.id.CzynnosciEditText);
            String czynnosciStr = czynnosciTV.getText().toString();
            Paragraph czynnosci = CreateEmail.createParagraph("6. Wykonane czynności podczas serwisu: ".concat(czynnosciStr), fontSize, noMargin);

            EditText podzespolyTV = (EditText)findViewById(R.id.PodzespolyEditText);
            String podzespolyStr = podzespolyTV.getText().toString();
            Paragraph podzespoly = CreateEmail.createParagraph("7. Wymienione podzespoły: ".concat(podzespolyStr), fontSize, noMargin);

            Paragraph parametry = CreateEmail.createParagraph("8. Parametry pracy urządzenia po przeprowadzonych czynnościach naprawczych:", fontSize, noMargin);

            //Cisnienie ssania
            EditText ssanieTV = (EditText)findViewById(R.id.SsanieEditText);
            String ssanieStr = ssanieTV.getText().toString();

            //Cisnienie oleju
            EditText olejuTV = (EditText)findViewById(R.id.OlejuEditText);
            String olejuStr = olejuTV.getText().toString();

            //Cisnienie sprezania
            EditText sprezanieTV = (EditText)findViewById(R.id.SprezaniaEditText);
            String sprezanieStr = sprezanieTV.getText().toString();

            //Temperatura freonu
            EditText freonuTV = (EditText)findViewById(R.id.FreonuEditText);
            String freonuStr = freonuTV.getText().toString();

            //Temperatura przegrzania
            EditText przegrzaniaTV = (EditText)findViewById(R.id.PrzegrzaniaEditText);
            String przegrzaniaStr = przegrzaniaTV.getText().toString();

            //Wydatek wody chlodzacej
            EditText wydatekTV = (EditText)findViewById(R.id.WydatekEditText);
            String wydatekStr = wydatekTV.getText().toString();

            Table cisnienie = CreateEmail.createTableCisnienie(
                    new float[]{200f, 100f, 200f, 100f},
                    new String[]{
                            "Ciśnienie ssania", ssanieStr.concat(" Bar"), "Temp. freonu", freonuStr.concat(" \u00B0C"),
                            "Ciśnienie oleju", olejuStr.concat(" Bar"), "Temp. przegrzania", przegrzaniaStr.concat(" \u00B0C"),
                            "Cisnienie sprężania", sprezanieStr.concat(" Bar"), "Wydatek wody chłodzacej", wydatekStr.concat(" 1/min")},
                    tableFontSize);

            //Temperatura wody chlodzacej skraplacz na wlocie
            EditText wlotTV = (EditText)findViewById(R.id.WlotEditText);
            String wlotStr = wlotTV.getText().toString();

            //Temperatura wody chlodzacej skraplacz na wylocie
            EditText wylotTV = (EditText)findViewById(R.id.WylotEditText);
            String wylotStr = wylotTV.getText().toString();

            //Temperatura powietrza przed parownikiem
            EditText przedTV = (EditText)findViewById(R.id.PrzedEditText);
            String przedStr = przedTV.getText().toString();

            //Temperatura powietrza za parownikiem
            EditText zaTV = (EditText)findViewById(R.id.ZaEditText);
            String zaStr = zaTV.getText().toString();

            Table temp = CreateEmail.createTableTemp(
                    new float[]{200f, 200f, 200f},
                    new String[]{
                            "Temp. wody chłodzacej skraplacz", "Wlot ".concat(wlotStr).concat(" \u00B0C"), "Wylot ".concat(wylotStr).concat(" \u00B0C"),
                            "Temp. powietrza", "Przed parownikiem ".concat(przedStr).concat(" \u00B0C"), "Za parownikiem ".concat(zaStr).concat(" \u00B0C")},
                    tableFontSize);

            //Potwierdzenie prawidlowosci dzialania wylacznikow bezpieczenstwa
            EditText potwierdzenieTV = (EditText)findViewById(R.id.PotwierdzenieEditText);
            String potwierdzenieStr = potwierdzenieTV.getText().toString();
            Paragraph potwierdzenie = CreateEmail.createParagraph("Potwierdzenie prawidłowości działania wylącznikow bezpieczeństwa i ich nastaw: ".concat(potwierdzenieStr), fontSize, noMargin);

            //Ilosc godzin naprawczych
            EditText godzinyTV = (EditText)findViewById(R.id.GodzinyTextEdit);
            String godzinyStr = godzinyTV.getText().toString();
            Paragraph ilosc = CreateEmail.createParagraph("9. Ilość przeprowadzonych godzin przez serwis: ".concat(godzinyStr), fontSize, noMargin);

            //Nazwiska pracownikow serwisu
            EditText nazwiskaTV = (EditText)findViewById(R.id.NazwiskaEditText);
            String nazwiskaStr = nazwiskaTV.getText().toString();
            Paragraph nazwiska = CreateEmail.createParagraph("a) nazwiska pracowników serwisu: ".concat(nazwiskaStr), fontSize, margin);

            //Kwalifikacja uslugi serwisowej
            RadioButton platna = (RadioButton)findViewById(R.id.PlatnaRadioButton);
            String kwalifikacjaStr = "";
            if(platna.isChecked()) {
                kwalifikacjaStr = "płatna";
            }
            else {
                kwalifikacjaStr = "nie płatna";
            }
            Paragraph kwalifikacja = CreateEmail.createParagraph("10. Usługa zakwalifikowana prez pracowników serwisu jako: ".concat(kwalifikacjaStr), fontSize, noMargin);

            //Uwagi i zalecenia serwisu
            EditText uwagiTV = (EditText)findViewById(R.id.UwagiEditText);
            String uwagiStr = uwagiTV.getText().toString();
            Paragraph uwagiSerwisu = CreateEmail.createParagraph("11. Uwagi i zalecenia serwisu: ".concat(uwagiStr), fontSize, noMargin);

            //Uwagi ze strony kopalni
            EditText uwagiKp = (EditText)findViewById(R.id.UwagiKopalniaEditText);
            String uwagiKpStr = uwagiKp.getText().toString();
            Paragraph uwagiKopalni = CreateEmail.createParagraph("12. Uwagi ze strony kopalni: ".concat(uwagiKpStr), fontSize, noMargin);

            Context context = this;
            Table pageHeadlineTable = CreateEmail.createPageLogos(context);

            SignaturePad signature = (SignaturePad)findViewById(R.id.WykonawcaSignaturePad);
            Bitmap bitmap = signature.getSignatureBitmap();
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
            Image wykonawca = new Image(ImageDataFactory.create(stream.toByteArray()));

            SignaturePad signatureZ = (SignaturePad)findViewById(R.id.ZleceniodawcaSignaturePad);
            Bitmap bitmapZ = signatureZ.getSignatureBitmap();
            ByteArrayOutputStream streamZ = new ByteArrayOutputStream();
            bitmapZ.compress(Bitmap.CompressFormat.PNG, 100, streamZ);
            Image zleceniodawca = new Image(ImageDataFactory.create(streamZ.toByteArray()));

            Table signatureArray = CreateEmail.createImageTable(new float[]{400, 400}, new Image[]{wykonawca, zleceniodawca});
            Table signatureDescription = CreateEmail.createTableNoBorder(new float[]{400, 400}, new String[]{"Wykonawca", "Zleceniodawca"}, fontSize);

            Paragraph[] arrayPB = {
                    dataParagraph,
                    pageHeadline,
                    kopalnia,
                    urzadzenie, typNumer,
                    zabudowa,
                    zgloszenie, osoba, dataPrzyczyna,
                    uszkodzenia,
                    czynnosci,
                    podzespoly,
                    parametry
            };

            Paragraph[] arrayPA = {
                    potwierdzenie,
                    ilosc, nazwiska,
                    kwalifikacja,
                    uwagiSerwisu,
                    uwagiKopalni
            };

            Table[] arrayT = {
                    cisnienie,
                    temp
            };

            Table[] arrayTB = {
                    signatureDescription,
                    signatureArray
            };

            CreateEmail.createAndSend(pageHeadlineTable ,arrayPB ,arrayT, arrayPA, arrayTB);
            Toast.makeText(this, "Stworzono plik PDF", Toast.LENGTH_LONG).show();
            File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), "protokol.pdf");
            Uri uri = FileProvider.getUriForFile(this, "com.example.protocole.fileprovider", file);

            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_SEND);
            intent.setDataAndType(uri, "application/pdf");
            intent.putExtra(Intent.EXTRA_EMAIL, new String[]{""});
            intent.putExtra(Intent.EXTRA_SUBJECT, "");
            intent.putExtra(Intent.EXTRA_TEXT, "");
            intent.putExtra(Intent.EXTRA_STREAM, uri);
            intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            startActivity(intent);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void clearWykonawcaSignature(View view) {
        SignaturePad signature = (SignaturePad)findViewById(R.id.WykonawcaSignaturePad);
        signature.clear();
    }

    public void clearZleceniodawcaSignature(View view) {
        SignaturePad signature = (SignaturePad)findViewById(R.id.ZleceniodawcaSignaturePad);
        signature.clear();
    }
}
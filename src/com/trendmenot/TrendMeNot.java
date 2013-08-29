package com.trendmenot;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.model.TextPiece;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.primefaces.component.chart.metergauge.MeterGaugeChart;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;
import org.primefaces.model.chart.MeterGaugeChartModel;

import com.alchmey.GetAnalysis;
import com.minemyrep.io.Input;
import com.minemyrep.io.Result;
import com.pipl.api.search.SearchAPIError;
import com.trendmenot.data.Email;

@ManagedBean
@SessionScoped
public class TrendMeNot {
	private Map<String,String> countries = new TreeMap<String, String>();
	
	private Input input = new Input();
	private List<Result> results = new ArrayList<Result>();

	public Map<String, String> getCountries() {
		return countries;
	}

	public void setCountries(Map<String, String> countries) {
		this.countries = countries;
	}

	public List<Result> getResults() {
		return results;
	}

	public void setResults(List<Result> results) {
		this.results = results;
	}

	public Input getInput() {
		return input;
	}

	public void setInput(Input input) {
		this.input = input;
	}

	private Email email = new Email();
	
	public Email getEmail() {
		return email;
	}

	public void setEmail(Email email) {
		this.email = email;
	}


	public TrendMeNot() {

//		List<Number> intervals = new ArrayList<Number>();
//
//		intervals.add(25);
//		intervals.add(50);
//		intervals.add(100);
//
//		fmeterGaugeModel = new MeterGaugeChartModel(80, intervals);
//
//		intervals.add(25);
//		intervals.add(50);
//		intervals.add(100);
//
//		tmeterGaugeModel = new MeterGaugeChartModel(70, intervals);
//
//		intervals.add(25);
//		intervals.add(50);
//		intervals.add(100);
//
//		lmeterGaugeModel = new MeterGaugeChartModel(90, intervals);
//
//		intervals.add(25);
//		intervals.add(50);
//		intervals.add(100);
//
//		gmeterGaugeModel = new MeterGaugeChartModel(40, intervals);

	}

	public void fileUpload(FileUploadEvent event) {
		System.out.println(event.getFile().getFileName());
		UploadedFile file = event.getFile();
		try {
			InputStream is = new ByteArrayInputStream(
					(byte[]) file.getContents());
			POIFSFileSystem fs = new POIFSFileSystem(is);
			HWPFDocument doc = new HWPFDocument(fs);
			Object[] textStack = doc.getTextTable().getTextPieces().toArray();
			String str = "";
			for (int i = 0; i < textStack.length; i++) {
				if (str == "") {
					str = ((TextPiece) textStack[i]).getStringBuffer()
							.toString();
				}

				else {
					str = str
							+ " "
							+ ((TextPiece) textStack[i]).getStringBuffer()
									.toString();

				}
			}
					

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public String sendEmail() {
		MineMyRepEmail sendEmail = new MineMyRepEmail();
		sendEmail.SendEmail(email);
		FacesContext.getCurrentInstance().addMessage(null,new FacesMessage("Thank you for your message"));
		return null;
	}
	
	public void listAllCountries()
	{
		countries = new HashMap<String, String>();
		countries.put("AFGHANISTAN","AF");
		countries.put("ÅLAND ISLANDS","AX");
		countries.put("ALBANIA","AL");
		countries.put("ALGERIA","DZ");
		countries.put("AMERICAN SAMOA","AS");
		countries.put("ANDORRA","AD");
		countries.put("ANGOLA","AO");
		countries.put("ANGUILLA","AI");
		countries.put("ANTARCTICA","AQ");
		countries.put("ANTIGUA AND BARBUDA","AG");
		countries.put("ARGENTINA","AR");
		countries.put("ARMENIA","AM");
		countries.put("ARUBA","AW");
		countries.put("AUSTRALIA","AU");
		countries.put("AUSTRIA","AT");
		countries.put("AZERBAIJAN","AZ");
		countries.put("BAHAMAS","BS");
		countries.put("BAHRAIN","BH");
		countries.put("BANGLADESH","BD");
		countries.put("BARBADOS","BB");
		countries.put("BELARUS","BY");
		countries.put("BELGIUM","BE");
		countries.put("BELIZE","BZ");
		countries.put("BENIN","BJ");
		countries.put("BERMUDA","BM");
		countries.put("BHUTAN","BT");
		countries.put("BOLIVIA, PLURINATIONAL STATE OF","BO");
		countries.put("BONAIRE, SINT EUSTATIUS AND SABA","BQ");
		countries.put("BOSNIA AND HERZEGOVINA","BA");
		countries.put("BOTSWANA","BW");
		countries.put("BOUVET ISLAND","BV");
		countries.put("BRAZIL","BR");
		countries.put("BRITISH INDIAN OCEAN TERRITORY","IO");
		countries.put("BRUNEI DARUSSALAM","BN");
		countries.put("BULGARIA","BG");
		countries.put("BURKINA FASO","BF");
		countries.put("BURUNDI","BI");
		countries.put("CAMBODIA","KH");
		countries.put("CAMEROON","CM");
		countries.put("CANADA","CA");
		countries.put("CAPE VERDE","CV");
		countries.put("CAYMAN ISLANDS","KY");
		countries.put("CENTRAL AFRICAN REPUBLIC","CF");
		countries.put("CHAD","TD");
		countries.put("CHILE","CL");
		countries.put("CHINA","CN");
		countries.put("CHRISTMAS ISLAND","CX");
		countries.put("COCOS (KEELING) ISLANDS","CC");
		countries.put("COLOMBIA","CO");
		countries.put("COMOROS","KM");
		countries.put("CONGO","CG");
		countries.put("CONGO, THE DEMOCRATIC REPUBLIC OF THE","CD");
		countries.put("COOK ISLANDS","CK");
		countries.put("COSTA RICA","CR");
		countries.put("CÔTE D'IVOIRE","CI");
		countries.put("CROATIA","HR");
		countries.put("CUBA","CU");
		countries.put("CURAÇAO","CW");
		countries.put("CYPRUS","CY");
		countries.put("CZECH REPUBLIC","CZ");
		countries.put("DENMARK","DK");
		countries.put("DJIBOUTI","DJ");
		countries.put("DOMINICA","DM");
		countries.put("DOMINICAN REPUBLIC","DO");
		countries.put("ECUADOR","EC");
		countries.put("EGYPT","EG");
		countries.put("EL SALVADOR","SV");
		countries.put("EQUATORIAL GUINEA","GQ");
		countries.put("ERITREA","ER");
		countries.put("ESTONIA","EE");
		countries.put("ETHIOPIA","ET");
		countries.put("FALKLAND ISLANDS (MALVINAS)","FK");
		countries.put("FAROE ISLANDS","FO");
		countries.put("FIJI","FJ");
		countries.put("FINLAND","FI");
		countries.put("FRANCE","FR");
		countries.put("FRENCH GUIANA","GF");
		countries.put("FRENCH POLYNESIA","PF");
		countries.put("FRENCH SOUTHERN TERRITORIES","TF");
		countries.put("GABON","GA");
		countries.put("GAMBIA","GM");
		countries.put("GEORGIA","GE");
		countries.put("GERMANY","DE");
		countries.put("GHANA","GH");
		countries.put("GIBRALTAR","GI");
		countries.put("GREECE","GR");
		countries.put("GREENLAND","GL");
		countries.put("GRENADA","GD");
		countries.put("GUADELOUPE","GP");
		countries.put("GUAM","GU");
		countries.put("GUATEMALA","GT");
		countries.put("GUERNSEY","GG");
		countries.put("GUINEA","GN");
		countries.put("GUINEA-BISSAU","GW");
		countries.put("GUYANA","GY");
		countries.put("HAITI","HT");
		countries.put("HEARD ISLAND AND MCDONALD ISLANDS","HM");
		countries.put("HOLY SEE (VATICAN CITY STATE)","VA");
		countries.put("HONDURAS","HN");
		countries.put("HONG KONG","HK");
		countries.put("HUNGARY","HU");
		countries.put("ICELAND","IS");
		countries.put("INDIA","IN");
		countries.put("INDONESIA","ID");
		countries.put("IRAN, ISLAMIC REPUBLIC OF","IR");
		countries.put("IRAQ","IQ");
		countries.put("IRELAND","IE");
		countries.put("ISLE OF MAN","IM");
		countries.put("ISRAEL","IL");
		countries.put("ITALY","IT");
		countries.put("JAMAICA","JM");
		countries.put("JAPAN","JP");
		countries.put("JERSEY","JE");
		countries.put("JORDAN","JO");
		countries.put("KAZAKHSTAN","KZ");
		countries.put("KENYA","KE");
		countries.put("KIRIBATI","KI");
		countries.put("KOREA, DEMOCRATIC PEOPLE'S REPUBLIC OF","KP");
		countries.put("KOREA, REPUBLIC OF","KR");
		countries.put("KUWAIT","KW");
		countries.put("KYRGYZSTAN","KG");
		countries.put("LAO PEOPLE'S DEMOCRATIC REPUBLIC","LA");
		countries.put("LATVIA","LV");
		countries.put("LEBANON","LB");
		countries.put("LESOTHO","LS");
		countries.put("LIBERIA","LR");
		countries.put("LIBYA","LY");
		countries.put("LIECHTENSTEIN","LI");
		countries.put("LITHUANIA","LT");
		countries.put("LUXEMBOURG","LU");
		countries.put("MACAO","MO");
		countries.put("MACEDONIA, THE FORMER YUGOSLAV REPUBLIC OF","MK");
		countries.put("MADAGASCAR","MG");
		countries.put("MALAWI","MW");
		countries.put("MALAYSIA","MY");
		countries.put("MALDIVES","MV");
		countries.put("MALI","ML");
		countries.put("MALTA","MT");
		countries.put("MARSHALL ISLANDS","MH");
		countries.put("MARTINIQUE","MQ");
		countries.put("MAURITANIA","MR");
		countries.put("MAURITIUS","MU");
		countries.put("MAYOTTE","YT");
		countries.put("MEXICO","MX");
		countries.put("MICRONESIA, FEDERATED STATES OF","FM");
		countries.put("MOLDOVA, REPUBLIC OF","MD");
		countries.put("MONACO","MC");
		countries.put("MONGOLIA","MN");
		countries.put("MONTENEGRO","ME");
		countries.put("MONTSERRAT","MS");
		countries.put("MOROCCO","MA");
		countries.put("MOZAMBIQUE","MZ");
		countries.put("MYANMAR","MM");
		countries.put("NAMIBIA","NA");
		countries.put("NAURU","NR");
		countries.put("NEPAL","NP");
		countries.put("NETHERLANDS","NL");
		countries.put("NEW CALEDONIA","NC");
		countries.put("NEW ZEALAND","NZ");
		countries.put("NICARAGUA","NI");
		countries.put("NIGER","NE");
		countries.put("NIGERIA","NG");
		countries.put("NIUE","NU");
		countries.put("NORFOLK ISLAND","NF");
		countries.put("NORTHERN MARIANA ISLANDS","MP");
		countries.put("NORWAY","NO");
		countries.put("OMAN","OM");
		countries.put("PAKISTAN","PK");
		countries.put("PALAU","PW");
		countries.put("PALESTINE, STATE OF","PS");
		countries.put("PANAMA","PA");
		countries.put("PAPUA NEW GUINEA","PG");
		countries.put("PARAGUAY","PY");
		countries.put("PERU","PE");
		countries.put("PHILIPPINES","PH");
		countries.put("PITCAIRN","PN");
		countries.put("POLAND","PL");
		countries.put("PORTUGAL","PT");
		countries.put("PUERTO RICO","PR");
		countries.put("QATAR","QA");
		countries.put("RÉUNION","RE");
		countries.put("ROMANIA","RO");
		countries.put("RUSSIAN FEDERATION","RU");
		countries.put("RWANDA","RW");
		countries.put("SAINT BARTHÉLEMY","BL");
		countries.put("SAINT HELENA, ASCENSION AND TRISTAN DA CUNHA","SH");
		countries.put("SAINT KITTS AND NEVIS","KN");
		countries.put("SAINT LUCIA","LC");
		countries.put("SAINT MARTIN (FRENCH PART)","MF");
		countries.put("SAINT PIERRE AND MIQUELON","PM");
		countries.put("SAINT VINCENT AND THE GRENADINES","VC");
		countries.put("SAMOA","WS");
		countries.put("SAN MARINO","SM");
		countries.put("SAO TOME AND PRINCIPE","ST");
		countries.put("SAUDI ARABIA","SA");
		countries.put("SENEGAL","SN");
		countries.put("SERBIA","RS");
		countries.put("SEYCHELLES","SC");
		countries.put("SIERRA LEONE","SL");
		countries.put("SINGAPORE","SG");
		countries.put("SINT MAARTEN (DUTCH PART)","SX");
		countries.put("SLOVAKIA","SK");
		countries.put("SLOVENIA","SI");
		countries.put("SOLOMON ISLANDS","SB");
		countries.put("SOMALIA","SO");
		countries.put("SOUTH AFRICA","ZA");
		countries.put("SOUTH GEORGIA AND THE SOUTH SANDWICH ISLANDS","GS");
		countries.put("SOUTH SUDAN","SS");
		countries.put("SPAIN","ES");
		countries.put("SRI LANKA","LK");
		countries.put("SUDAN","SD");
		countries.put("SURINAME","SR");
		countries.put("SVALBARD AND JAN MAYEN","SJ");
		countries.put("SWAZILAND","SZ");
		countries.put("SWEDEN","SE");
		countries.put("SWITZERLAND","CH");
		countries.put("SYRIAN ARAB REPUBLIC","SY");
		countries.put("TAIWAN, PROVINCE OF CHINA","TW");
		countries.put("TAJIKISTAN","TJ");
		countries.put("TANZANIA, UNITED REPUBLIC OF","TZ");
		countries.put("THAILAND","TH");
		countries.put("TIMOR-LESTE","TL");
		countries.put("TOGO","TG");
		countries.put("TOKELAU","TK");
		countries.put("TONGA","TO");
		countries.put("TRINIDAD AND TOBAGO","TT");
		countries.put("TUNISIA","TN");
		countries.put("TURKEY","TR");
		countries.put("TURKMENISTAN","TM");
		countries.put("TURKS AND CAICOS ISLANDS","TC");
		countries.put("TUVALU","TV");
		countries.put("UGANDA","UG");
		countries.put("UKRAINE","UA");
		countries.put("UNITED ARAB EMIRATES","AE");
		countries.put("UNITED KINGDOM","GB");
		countries.put("UNITED STATES","US");
		countries.put("UNITED STATES MINOR OUTLYING ISLANDS","UM");
		countries.put("URUGUAY","UY");
		countries.put("UZBEKISTAN","UZ");
		countries.put("VANUATU","VU");
		countries.put("VENEZUELA, BOLIVARIAN REPUBLIC OF","VE");
		countries.put("VIET NAM","VN");
		countries.put("VIRGIN ISLANDS, BRITISH","VG");
		countries.put("VIRGIN ISLANDS, U.S.","VI");
		countries.put("WALLIS AND FUTUNA","WF");
		countries.put("WESTERN SAHARA","EH");
		countries.put("YEMEN","YE");
		countries.put("ZAMBIA","ZM");
		countries.put("ZIMBABWE","ZW");
	}
	
	public String mineMe() {
		GetAnalysis analyze = new GetAnalysis();
		List<Number> intervals = new ArrayList<Number>();
		double rat = 0;
		try {
			results = analyze.MineRep(input);
			
			intervals.add(0);
			intervals.add(250);
			intervals.add(500);
			intervals.add(750);
			intervals.add(1000);
			
			Number resultVal = 0;
			for(Result result: results) {
				System.out.println(result.getSentiment_weightage());
				if(result.getSentiment_weightage() == null) {
					resultVal = 0;
					rat = 0;
				} else if(result.getSentiment_weightage().matches("[A-Za-z]*")) {
					if(result.getSentiment_weightage().matches("positive"))
					{
						resultVal = 1000;
						rat = 10;
					} else if(result.getSentiment_weightage().matches("negative")) {
						resultVal = 0;
						rat = 0;
					} else {
						resultVal = 500;
						rat = 5;
					}
				}
				else {
					NumberFormat format = NumberFormat.getNumberInstance(Locale.US);
					resultVal = format.parse(result.getSentiment_weightage());
					Double res = resultVal.doubleValue();
					res = (res + 1) * 500;
					rat = Math.ceil((resultVal.doubleValue()+1)*5);
					
					Integer i = (int) rat;
					System.out.println("res "+res);
					System.out.println("rat "+rat);
					System.out.println("i "+i);
					resultVal = format.parse(res.toString());
					result.setRating(i);
					System.out.println(resultVal);
				}
				MeterGaugeChartModel meter = new MeterGaugeChartModel(resultVal,intervals);
				result.setMeter(meter);
				Double resCorrelation = Double.parseDouble(result.getresumeCorrelation());
				resCorrelation = (resCorrelation * 100)+20;
				result.setResumeCorrelation(resCorrelation+"%");
				
			}
		} catch (SearchAPIError e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return "/analysis.xhtml?faces-redirect=true";
	}
	
	public String returnToMine() {
		input = new Input();
		return "/input.xhtml?faces-redirect=true";
	}
 }

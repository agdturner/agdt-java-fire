/*
 * Copyright 2018 Andy Turner, CCG, University of Leeds.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package uk.ac.leeds.ccg.projects.fire.data;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import uk.ac.leeds.ccg.projects.fire.core.F_Environment;
import uk.ac.leeds.ccg.projects.fire.core.F_Object;
import uk.ac.leeds.ccg.projects.fire.core.F_Strings;
import uk.ac.leeds.ccg.projects.fire.id.F_CollectionID;
import uk.ac.leeds.ccg.projects.fire.id.F_RecordID;
import uk.ac.leeds.ccg.generic.io.Generic_FileStore;
import uk.ac.leeds.ccg.generic.io.Generic_IO;

/**
 * F_Data
 *
 * @author Andy Turner
 * @version 1.0.0
 */
public class F_Data extends F_Object {

    private static final long serialVersionUID = 1L;

    public final Generic_FileStore fs;

    /**
     * The main data store. Keys are Collection IDs.
     */
    public Map<F_CollectionID, F_Collection> data;

    /**
     * The main data store. Keys are Collection IDs.
     */
    public Map<F_CollectionID, Set<F_RecordID>> cID2recIDs;
    
    /**
     * The main data store. Keys are Collection IDs.
     */
    public Map<F_RecordID, F_CollectionID> recID2cID;
    
    /**
     * Day of Week
     * <ul>
     * <li>code,label</li>
     * <li>1,Sunday</li>
     * <li>2,Monday</li>
     * <li>3,Tuesday</li>
     * <li>4,Wednesday</li>
     * <li>5,Thursday</li>
     * <li>6,Friday</li>
     * <li>7,Saturday</li>
     * </ul>
     */
    HashMap<Byte, String> dowid2dow;

    /**
     * @return {@link #dowid2dow} initialised first if it is {@code null}.
     */
    public HashMap<Byte, String> getDowid2dow() {
        if (dowid2dow == null) {
            dowid2dow = new HashMap<>();
            byte b = 1;
            dowid2dow.put(b, "Sunday");
            b = 2;
            dowid2dow.put(b, "Monday");
            b = 3;
            dowid2dow.put(b, "Tuesday");
            b = 4;
            dowid2dow.put(b, "Wednesday");
            b = 5;
            dowid2dow.put(b, "Thursday");
            b = 6;
            dowid2dow.put(b, "Friday");
            b = 7;
            dowid2dow.put(b, "Saturday");
        }
        return dowid2dow;
    }

    /**
     * <ul>
     * <li>code,label</li>
     * <li>1,Westminster</li>
     * <li>2,Camden</li>
     * <li>3,Islington</li>
     * <li>4,Hackney</li>
     * <li>5,Tower Hamlets</li>
     * <li>6,Greenwich</li>
     * <li>7,Lewisham</li>
     * <li>8,Southwark</li>
     * <li>9,Lambeth</li>
     * <li>10,Wandsworth</li>
     * <li>11,Hammersmith and Fulham</li>
     * <li>12,Kensington and Chelsea</li>
     * <li>13,Waltham Forest</li>
     * <li>14,Redbridge</li>
     * <li>15,Havering</li>
     * <li>16,Barking and Dagenham</li>
     * <li>17,Newham</li>
     * <li>18,Bexley</li>
     * <li>19,Bromley</li>
     * <li>20,Croydon</li>
     * <li>21,Sutton</li>
     * <li>22,Merton</li>
     * <li>23,Kingston upon Thames</li>
     * <li>24,Richmond upon Thames</li>
     * <li>25,Hounslow</li>
     * <li>26,Hillingdon</li>
     * <li>27,Ealing</li>
     * <li>28,Brent</li>
     * <li>29,Harrow</li>
     * <li>30,Barnet</li>
     * <li>31,Haringey</li>
     * <li>32,Enfield</li>
     * <li>33,Hertsmere</li>
     * <li>38,Epsom and Ewell</li>
     * <li>40,Spelthorne</li>
     * <li>57,London Airport (Heathrow)</li>
     * <li>60,Allerdale</li>
     * <li>61,Barrow-in-Furness</li>
     * <li>62,Carlisle</li>
     * <li>63,Copeland</li>
     * <li>64,Eden</li>
     * <li>65,South Lakeland</li>
     * <li>70,Blackburn with Darwen</li>
     * <li>71,Blackpool</li>
     * <li>72,Burnley</li>
     * <li>73,Chorley</li>
     * <li>74,Fylde</li>
     * <li>75,Hyndburn</li>
     * <li>76,Lancaster</li>
     * <li>77,Pendle</li>
     * <li>79,Preston</li>
     * <li>80,Ribble Valley</li>
     * <li>82,Rossendale</li>
     * <li>83,South Ribble</li>
     * <li>84,West Lancashire</li>
     * <li>85,Wyre</li>
     * <li>90,Knowsley</li>
     * <li>91,Liverpool</li>
     * <li>92,St. Helens</li>
     * <li>93,Sefton</li>
     * <li>95,Wirral</li>
     * <li>100,Bolton</li>
     * <li>101,Bury</li>
     * <li>102,Manchester</li>
     * <li>104,Oldham</li>
     * <li>106,Rochdale</li>
     * <li>107,Salford</li>
     * <li>109,Stockport</li>
     * <li>110,Tameside</li>
     * <li>112,Trafford</li>
     * <li>114,Wigan</li>
     * <li>120,Chester</li>
     * <li>121,Congleton</li>
     * <li>122,Crewe and Nantwich</li>
     * <li>123,Ellesmere Port and Neston</li>
     * <li>124,Halton</li>
     * <li>126,Macclesfield</li>
     * <li>127,Vale Royal</li>
     * <li>128,Warrington</li>
     * <li>129,Cheshire East</li>
     * <li>130,Cheshire West and Chester</li>
     * <li>139,Northumberland</li>
     * <li>140,Alnwick</li>
     * <li>141,Berwick-upon-Tweed</li>
     * <li>142,Blyth Valley</li>
     * <li>143,Castle Morpeth</li>
     * <li>144,Tynedale</li>
     * <li>145,Wansbeck</li>
     * <li>146,Gateshead</li>
     * <li>147,Newcastle upon Tyne</li>
     * <li>148,North Tyneside</li>
     * <li>149,South Tyneside</li>
     * <li>150,Sunderland</li>
     * <li>160,Chester-le-Street</li>
     * <li>161,Darlington</li>
     * <li>162,Derwentside</li>
     * <li>163,Durham</li>
     * <li>164,Easington</li>
     * <li>165,Sedgefield</li>
     * <li>166,Teesdale</li>
     * <li>168,Wear Valley</li>
     * <li>169,County Durham</li>
     * <li>180,Craven</li>
     * <li>181,Hambleton</li>
     * <li>182,Harrogate</li>
     * <li>184,Richmondshire</li>
     * <li>185,Ryedale</li>
     * <li>186,Scarborough</li>
     * <li>187,Selby</li>
     * <li>189,York</li>
     * <li>200,Bradford</li>
     * <li>202,Calderdale</li>
     * <li>203,Kirklees</li>
     * <li>204,Leeds</li>
     * <li>206,Wakefield</li>
     * <li>210,Barnsley</li>
     * <li>211,Doncaster</li>
     * <li>213,Rotherham</li>
     * <li>215,Sheffield</li>
     * <li>228,"Kingston upon Hull, City of"</li>
     * <li>231,East Riding of Yorkshire</li>
     * <li>232,North Lincolnshire</li>
     * <li>233,North East Lincolnshire</li>
     * <li>240,Hartlepool</li>
     * <li>241,Redcar and Cleveland</li>
     * <li>243,Middlesbrough</li>
     * <li>245,Stockton-on-Tees</li>
     * <li>250,Cannock Chase</li>
     * <li>251,East Staffordshire</li>
     * <li>252,Lichfield</li>
     * <li>253,Newcastle-under-Lyme</li>
     * <li>254,South Staffordshire</li>
     * <li>255,Stafford</li>
     * <li>256,Staffordshire Moorlands</li>
     * <li>257,Stoke-on-Trent</li>
     * <li>258,Tamworth</li>
     * <li>270,Bromsgrove</li>
     * <li>273,Malvern Hills</li>
     * <li>274,Redditch</li>
     * <li>276,Worcester</li>
     * <li>277,Wychavon</li>
     * <li>278,Wyre Forest</li>
     * <li>279,Bridgnorth</li>
     * <li>280,North Shropshire</li>
     * <li>281,Oswestry</li>
     * <li>282,Shrewsbury and Atcham</li>
     * <li>283,South Shropshire</li>
     * <li>284,Telford and Wrekin</li>
     * <li>285,"Herefordshire, County of "</li>
     * <li>286,Shropshire</li>
     * <li>290,North Warwickshire</li>
     * <li>291,Nuneaton and Bedworth</li>
     * <li>292,Rugby </li>
     * <li>293,Stratford-upon-Avon</li>
     * <li>294,Warwick</li>
     * <li>300,Birmingham</li>
     * <li>302,Coventry</li>
     * <li>303,Dudley</li>
     * <li>305,Sandwell</li>
     * <li>306,Solihull</li>
     * <li>307,Walsall</li>
     * <li>309,Wolverhampton</li>
     * <li>320,Amber Valley</li>
     * <li>321,Bolsover</li>
     * <li>322,Chesterfield</li>
     * <li>323,Derby</li>
     * <li>324,Erewash</li>
     * <li>325,High Peak</li>
     * <li>327,North East Derbyshire</li>
     * <li>328,South Derbyshire</li>
     * <li>329,Derbyshire Dales</li>
     * <li>340,Ashfield</li>
     * <li>341,Bassetlaw</li>
     * <li>342,Broxtowe</li>
     * <li>343,Gedling</li>
     * <li>344,Mansfield</li>
     * <li>345,Newark and Sherwood</li>
     * <li>346,Nottingham</li>
     * <li>347,Rushcliffe</li>
     * <li>350,Boston</li>
     * <li>351,East Lindsey</li>
     * <li>352,Lincoln</li>
     * <li>353,North Kesteven</li>
     * <li>354,South Holland</li>
     * <li>355,South Kesteven</li>
     * <li>356,West Lindsey</li>
     * <li>360,Blaby</li>
     * <li>361,Hinckley and Bosworth</li>
     * <li>362,Charnwood</li>
     * <li>363,Harborough</li>
     * <li>364,Leicester</li>
     * <li>365,Melton</li>
     * <li>366,North West Leicestershire</li>
     * <li>367,Oadby and Wigston</li>
     * <li>368,Rutland</li>
     * <li>380,Corby</li>
     * <li>381,Daventry</li>
     * <li>382,East Northamptonshire</li>
     * <li>383,Kettering</li>
     * <li>384,Northampton</li>
     * <li>385,South Northamptonshire</li>
     * <li>386,Wellingborough</li>
     * <li>390,Cambridge</li>
     * <li>391,East Cambridgeshire</li>
     * <li>392,Fenland</li>
     * <li>393,Huntingdonshire</li>
     * <li>394,Peterborough</li>
     * <li>395,South Cambridgeshire</li>
     * <li>400,Breckland</li>
     * <li>401,Broadland</li>
     * <li>402,Great Yarmouth</li>
     * <li>404,Norwich</li>
     * <li>405,North Norfolk</li>
     * <li>406,South Norfolk</li>
     * <li>407,King's Lynn and West Norfolk</li>
     * <li>410,Babergh</li>
     * <li>411,Forest Heath</li>
     * <li>412,Ipswich</li>
     * <li>413,Mid Suffolk</li>
     * <li>414,St. Edmundsbury</li>
     * <li>415,Suffolk Coastal</li>
     * <li>416,Waveney</li>
     * <li>420,Bedford</li>
     * <li>421,Luton</li>
     * <li>422,Mid Bedfordshire</li>
     * <li>423,South Bedfordshire</li>
     * <li>424,Central Bedfordshire</li>
     * <li>430,Broxbourne</li>
     * <li>431,Dacorum</li>
     * <li>432,East Hertfordshire</li>
     * <li>433,North Hertfordshire</li>
     * <li>434,St. Albans</li>
     * <li>435,Stevenage</li>
     * <li>436,Three Rivers</li>
     * <li>437,Watford</li>
     * <li>438,Welwyn Hatfield</li>
     * <li>450,Basildon</li>
     * <li>451,Braintree</li>
     * <li>452,Brentwood</li>
     * <li>453,Castle Point</li>
     * <li>454,Chelmsford</li>
     * <li>455,Colchester</li>
     * <li>456,Epping Forest</li>
     * <li>457,Harlow</li>
     * <li>458,Maldon</li>
     * <li>459,Rochford</li>
     * <li>460,Southend-on-Sea</li>
     * <li>461,Tendring</li>
     * <li>462,Thurrock</li>
     * <li>463,Uttlesford</li>
     * <li>470,Bracknell Forest</li>
     * <li>471,West Berkshire</li>
     * <li>472,Reading</li>
     * <li>473,Slough</li>
     * <li>474,Windsor and Maidenhead</li>
     * <li>475,Wokingham</li>
     * <li>476,Aylesbury Vale</li>
     * <li>477,South Bucks</li>
     * <li>478,Chiltern</li>
     * <li>479,Milton Keynes</li>
     * <li>480,Wycombe</li>
     * <li>481,Cherwell</li>
     * <li>482,Oxford</li>
     * <li>483,Vale of White Horse</li>
     * <li>484,South Oxfordshire</li>
     * <li>485,West Oxfordshire</li>
     * <li>490,Basingstoke and Deane</li>
     * <li>491,Eastleigh</li>
     * <li>492,Fareham</li>
     * <li>493,Gosport</li>
     * <li>494,Hart</li>
     * <li>495,Havant</li>
     * <li>496,New Forest</li>
     * <li>497,East Hampshire</li>
     * <li>498,Portsmouth</li>
     * <li>499,Rushmoor</li>
     * <li>500,Southampton </li>
     * <li>501,Test Valley</li>
     * <li>502,Winchester</li>
     * <li>505,Isle of Wight</li>
     * <li>510,Elmbridge</li>
     * <li>511,Guildford</li>
     * <li>512,Mole Valley</li>
     * <li>513,Reigate and Banstead</li>
     * <li>514,Runnymede</li>
     * <li>515,Surrey Heath</li>
     * <li>516,Tandridge</li>
     * <li>517,Waverley</li>
     * <li>518,Woking</li>
     * <li>530,Ashford</li>
     * <li>531,Canterbury</li>
     * <li>532,Dartford</li>
     * <li>533,Dover</li>
     * <li>535,Gravesham</li>
     * <li>536,Maidstone</li>
     * <li>538,Sevenoaks</li>
     * <li>539,Shepway</li>
     * <li>540,Swale</li>
     * <li>541,Thanet</li>
     * <li>542,Tonbridge and Malling</li>
     * <li>543,Tunbridge Wells</li>
     * <li>544,Medway</li>
     * <li>551,Eastbourne</li>
     * <li>552,Hastings</li>
     * <li>554,Lewes</li>
     * <li>555,Rother</li>
     * <li>556,Wealden</li>
     * <li>557,Adur</li>
     * <li>558,Arun</li>
     * <li>559,Chichester</li>
     * <li>560,Crawley</li>
     * <li>562,Horsham</li>
     * <li>563,Mid Sussex</li>
     * <li>564,Worthing</li>
     * <li>565,Brighton and Hove</li>
     * <li>570,City of London</li>
     * <li>580,East Devon</li>
     * <li>581,Exeter</li>
     * <li>582,North Devon</li>
     * <li>583,Plymouth</li>
     * <li>584,South Hams</li>
     * <li>585,Teignbridge</li>
     * <li>586,Mid Devon</li>
     * <li>587,Torbay</li>
     * <li>588,Torridge</li>
     * <li>589,West Devon</li>
     * <li>590,Caradon</li>
     * <li>591,Carrick</li>
     * <li>592,Kerrier</li>
     * <li>593,North Cornwall</li>
     * <li>594,Penwith</li>
     * <li>595,Restormel</li>
     * <li>596,Cornwall</li>
     * <li>601,"Bristol, City of"</li>
     * <li>605,North Somerset</li>
     * <li>606,Mendip</li>
     * <li>607,Sedgemoor</li>
     * <li>608,Taunton Deane</li>
     * <li>609,West Somerset</li>
     * <li>610,South Somerset</li>
     * <li>611,Bath and North East Somerset</li>
     * <li>612,South Gloucestershire</li>
     * <li>620,Cheltenham</li>
     * <li>621,Cotswold</li>
     * <li>622,Forest of Dean</li>
     * <li>623,Gloucester</li>
     * <li>624,Stroud</li>
     * <li>625,Tewkesbury</li>
     * <li>630,Kennet</li>
     * <li>631,North Wiltshire</li>
     * <li>632,Salisbury</li>
     * <li>633,Swindon</li>
     * <li>634,West Wiltshire</li>
     * <li>635,Wiltshire</li>
     * <li>640,Bournemouth</li>
     * <li>641,Christchurch</li>
     * <li>642,North Dorset</li>
     * <li>643,Poole</li>
     * <li>644,Purbeck</li>
     * <li>645,West Dorset</li>
     * <li>646,Weymouth and Portland</li>
     * <li>647,East Dorset</li>
     * <li>720,Isle of Anglesey</li>
     * <li>721,Conwy</li>
     * <li>722,Gwynedd</li>
     * <li>723,Denbighshire</li>
     * <li>724,Flintshire</li>
     * <li>725,Wrexham</li>
     * <li>730,Blaenau Gwent</li>
     * <li>731,Caerphilly</li>
     * <li>732,Monmouthshire</li>
     * <li>733,Newport</li>
     * <li>734,Torfaen</li>
     * <li>740,Bridgend</li>
     * <li>741,Cardiff</li>
     * <li>742,Merthyr Tydfil</li>
     * <li>743,Neath Port Talbot</li>
     * <li>744,"Rhondda, Cynon, Taff"</li>
     * <li>745,Swansea</li>
     * <li>746,The Vale of Glamorgan</li>
     * <li>750,Ceredigion</li>
     * <li>751,Carmarthenshire</li>
     * <li>752,Pembrokeshire</li>
     * <li>753,Powys</li>
     * <li>910,Aberdeen City</li>
     * <li>911,Aberdeenshire</li>
     * <li>912,Angus</li>
     * <li>913,Argyll and Bute</li>
     * <li>914,Scottish Borders</li>
     * <li>915,Clackmannanshire</li>
     * <li>916,West Dunbartonshire</li>
     * <li>917,Dumfries and Galloway</li>
     * <li>918,Dundee City</li>
     * <li>919,East Ayrshire</li>
     * <li>920,East Dunbartonshire</li>
     * <li>921,East Lothian</li>
     * <li>922,East Renfrewshire</li>
     * <li>923,"Edinburgh, City of"</li>
     * <li>924,Falkirk</li>
     * <li>925,Fife</li>
     * <li>926,Glasgow City</li>
     * <li>927,Highland</li>
     * <li>928,Inverclyde</li>
     * <li>929,Midlothian</li>
     * <li>930,Moray</li>
     * <li>931,North Ayrshire</li>
     * <li>932,North Lanarkshire</li>
     * <li>933,Orkney Islands</li>
     * <li>934,Perth and Kinross</li>
     * <li>935,Renfrewshire</li>
     * <li>936,Shetland Islands</li>
     * <li>937,South Ayrshire</li>
     * <li>938,South Lanarkshire</li>
     * <li>939,Stirling</li>
     * <li>940,West Lothian</li>
     * <li>941,Western Isles</li>
     * </ul>
     */
    HashMap<Short, String> ladid2lad;

    /**
     * @return {@link #ladid2lad} initialised first if it is {@code null}.
     */
    public HashMap<Short, String> getLadid2lad() {
        if (ladid2lad == null) {
            ladid2lad = new HashMap<>();
            short s = 1;
            ladid2lad.put(s, "Westminster");
            s = 2;
            ladid2lad.put(s, "Camden");
            s = 3;
            ladid2lad.put(s, "Islington");
            s = 4;
            ladid2lad.put(s, "Hackney");
            s = 5;
            ladid2lad.put(s, "Tower Hamlets");
            s = 6;
            ladid2lad.put(s, "Greenwich");
            s = 7;
            ladid2lad.put(s, "Lewisham");
            s = 8;
            ladid2lad.put(s, "Southwark");
            s = 9;
            ladid2lad.put(s, "Lambeth");
            s = 10;
            ladid2lad.put(s, "Wandsworth");
            s = 11;
            ladid2lad.put(s, "Hammersmith and Fulham");
            s = 12;
            ladid2lad.put(s, "Kensington and Chelsea");
            s = 13;
            ladid2lad.put(s, "Waltham Forest");
            s = 14;
            ladid2lad.put(s, "Redbridge");
            s = 15;
            ladid2lad.put(s, "Havering");
            s = 16;
            ladid2lad.put(s, "Barking and Dagenham");
            s = 17;
            ladid2lad.put(s, "Newham");
            s = 18;
            ladid2lad.put(s, "Bexley");
            s = 19;
            ladid2lad.put(s, "Bromley");
            s = 20;
            ladid2lad.put(s, "Croydon");
            s = 21;
            ladid2lad.put(s, "Sutton");
            s = 22;
            ladid2lad.put(s, "Merton");
            s = 23;
            ladid2lad.put(s, "Kingston upon Thames");
            s = 24;
            ladid2lad.put(s, "Richmond upon Thames");
            s = 25;
            ladid2lad.put(s, "Hounslow");
            s = 26;
            ladid2lad.put(s, "Hillingdon");
            s = 27;
            ladid2lad.put(s, "Ealing");
            s = 28;
            ladid2lad.put(s, "Brent");
            s = 29;
            ladid2lad.put(s, "Harrow");
            s = 30;
            ladid2lad.put(s, "Barnet");
            s = 31;
            ladid2lad.put(s, "Haringey");
            s = 32;
            ladid2lad.put(s, "Enfield");
            s = 33;
            ladid2lad.put(s, "Hertsmere");
            s = 38;
            ladid2lad.put(s, "Epsom and Ewell");
            s = 40;
            ladid2lad.put(s, "Spelthorne");
            s = 57;
            ladid2lad.put(s, "London Airport (Heathrow)");
            s = 60;
            ladid2lad.put(s, "Allerdale");
            s = 61;
            ladid2lad.put(s, "Barrow-in-Furness");
            s = 62;
            ladid2lad.put(s, "Carlisle");
            s = 63;
            ladid2lad.put(s, "Copeland");
            s = 64;
            ladid2lad.put(s, "Eden");
            s = 65;
            ladid2lad.put(s, "South Lakeland");
            s = 70;
            ladid2lad.put(s, "Blackburn with Darwen");
            s = 71;
            ladid2lad.put(s, "Blackpool");
            s = 72;
            ladid2lad.put(s, "Burnley");
            s = 73;
            ladid2lad.put(s, "Chorley");
            s = 74;
            ladid2lad.put(s, "Fylde");
            s = 75;
            ladid2lad.put(s, "Hyndburn");
            s = 76;
            ladid2lad.put(s, "Lancaster");
            s = 77;
            ladid2lad.put(s, "Pendle");
            s = 79;
            ladid2lad.put(s, "Preston");
            s = 80;
            ladid2lad.put(s, "Ribble Valley");
            s = 82;
            ladid2lad.put(s, "Rossendale");
            s = 83;
            ladid2lad.put(s, "South Ribble");
            s = 84;
            ladid2lad.put(s, "West Lancashire");
            s = 85;
            ladid2lad.put(s, "Wyre");
            s = 90;
            ladid2lad.put(s, "Knowsley");
            s = 91;
            ladid2lad.put(s, "Liverpool");
            s = 92;
            ladid2lad.put(s, "St. Helens");
            s = 93;
            ladid2lad.put(s, "Sefton");
            s = 95;
            ladid2lad.put(s, "Wirral");
            s = 100;
            ladid2lad.put(s, "Bolton");
            s = 101;
            ladid2lad.put(s, "Bury");
            s = 102;
            ladid2lad.put(s, "Manchester");
            s = 104;
            ladid2lad.put(s, "Oldham");
            s = 106;
            ladid2lad.put(s, "Rochdale");
            s = 107;
            ladid2lad.put(s, "Salford");
            s = 109;
            ladid2lad.put(s, "Stockport");
            s = 110;
            ladid2lad.put(s, "Tameside");
            s = 112;
            ladid2lad.put(s, "Trafford");
            s = 114;
            ladid2lad.put(s, "Wigan");
            s = 120;
            ladid2lad.put(s, "Chester");
            s = 121;
            ladid2lad.put(s, "Congleton");
            s = 122;
            ladid2lad.put(s, "Crewe and Nantwich");
            s = 123;
            ladid2lad.put(s, "Ellesmere Port and Neston");
            s = 124;
            ladid2lad.put(s, "Halton");
            s = 126;
            ladid2lad.put(s, "Macclesfield");
            s = 127;
            ladid2lad.put(s, "Vale Royal");
            s = 128;
            ladid2lad.put(s, "Warrington");
            s = 129;
            ladid2lad.put(s, "Cheshire East");
            s = 130;
            ladid2lad.put(s, "Cheshire West and Chester");
            s = 139;
            ladid2lad.put(s, "Northumberland");
            s = 140;
            ladid2lad.put(s, "Alnwick");
            s = 141;
            ladid2lad.put(s, "Berwick-upon-Tweed");
            s = 142;
            ladid2lad.put(s, "Blyth Valley");
            s = 143;
            ladid2lad.put(s, "Castle Morpeth");
            s = 144;
            ladid2lad.put(s, "Tynedale");
            s = 145;
            ladid2lad.put(s, "Wansbeck");
            s = 146;
            ladid2lad.put(s, "Gateshead");
            s = 147;
            ladid2lad.put(s, "Newcastle upon Tyne");
            s = 148;
            ladid2lad.put(s, "North Tyneside");
            s = 149;
            ladid2lad.put(s, "South Tyneside");
            s = 150;
            ladid2lad.put(s, "Sunderland");
            s = 160;
            ladid2lad.put(s, "Chester-le-Street");
            s = 161;
            ladid2lad.put(s, "Darlington");
            s = 162;
            ladid2lad.put(s, "Derwentside");
            s = 163;
            ladid2lad.put(s, "Durham");
            s = 164;
            ladid2lad.put(s, "Easington");
            s = 165;
            ladid2lad.put(s, "Sedgefield");
            s = 166;
            ladid2lad.put(s, "Teesdale");
            s = 168;
            ladid2lad.put(s, "Wear Valley");
            s = 169;
            ladid2lad.put(s, "County Durham");
            s = 180;
            ladid2lad.put(s, "Craven");
            s = 181;
            ladid2lad.put(s, "Hambleton");
            s = 182;
            ladid2lad.put(s, "Harrogate");
            s = 184;
            ladid2lad.put(s, "Richmondshire");
            s = 185;
            ladid2lad.put(s, "Ryedale");
            s = 186;
            ladid2lad.put(s, "Scarborough");
            s = 187;
            ladid2lad.put(s, "Selby");
            s = 189;
            ladid2lad.put(s, "York");
            s = 200;
            ladid2lad.put(s, "Bradford");
            s = 202;
            ladid2lad.put(s, "Calderdale");
            s = 203;
            ladid2lad.put(s, "Kirklees");
            s = 204;
            ladid2lad.put(s, "Leeds");
            s = 206;
            ladid2lad.put(s, "Wakefield");
            s = 210;
            ladid2lad.put(s, "Barnsley");
            s = 211;
            ladid2lad.put(s, "Doncaster");
            s = 213;
            ladid2lad.put(s, "Rotherham");
            s = 215;
            ladid2lad.put(s, "Sheffield");
            s = 228;
            ladid2lad.put(s, "Kingston upon Hull");
            s = 231;
            ladid2lad.put(s, "East Riding of Yorkshire");
            s = 232;
            ladid2lad.put(s, "North Lincolnshire");
            s = 233;
            ladid2lad.put(s, "North East Lincolnshire");
            s = 240;
            ladid2lad.put(s, "Hartlepool");
            s = 241;
            ladid2lad.put(s, "Redcar and Cleveland");
            s = 243;
            ladid2lad.put(s, "Middlesbrough");
            s = 245;
            ladid2lad.put(s, "Stockton-on-Tees");
            s = 250;
            ladid2lad.put(s, "Cannock Chase");
            s = 251;
            ladid2lad.put(s, "East Staffordshire");
            s = 252;
            ladid2lad.put(s, "Lichfield");
            s = 253;
            ladid2lad.put(s, "Newcastle-under-Lyme");
            s = 254;
            ladid2lad.put(s, "South Staffordshire");
            s = 255;
            ladid2lad.put(s, "Stafford");
            s = 256;
            ladid2lad.put(s, "Staffordshire Moorlands");
            s = 257;
            ladid2lad.put(s, "Stoke-on-Trent");
            s = 258;
            ladid2lad.put(s, "Tamworth");
            s = 270;
            ladid2lad.put(s, "Bromsgrove");
            s = 273;
            ladid2lad.put(s, "Malvern Hills");
            s = 274;
            ladid2lad.put(s, "Redditch");
            s = 276;
            ladid2lad.put(s, "Worcester");
            s = 277;
            ladid2lad.put(s, "Wychavon");
            s = 278;
            ladid2lad.put(s, "Wyre Forest");
            s = 279;
            ladid2lad.put(s, "Bridgnorth");
            s = 280;
            ladid2lad.put(s, "North Shropshire");
            s = 281;
            ladid2lad.put(s, "Oswestry");
            s = 282;
            ladid2lad.put(s, "Shrewsbury and Atcham");
            s = 283;
            ladid2lad.put(s, "South Shropshire");
            s = 284;
            ladid2lad.put(s, "Telford and Wrekin");
            s = 285;
            ladid2lad.put(s, "Herefordshire");
            s = 286;
            ladid2lad.put(s, "Shropshire");
            s = 290;
            ladid2lad.put(s, "North Warwickshire");
            s = 291;
            ladid2lad.put(s, "Nuneaton and Bedworth");
            s = 292;
            ladid2lad.put(s, "Rugby ");
            s = 293;
            ladid2lad.put(s, "Stratford-upon-Avon");
            s = 294;
            ladid2lad.put(s, "Warwick");
            s = 300;
            ladid2lad.put(s, "Birmingham");
            s = 302;
            ladid2lad.put(s, "Coventry");
            s = 303;
            ladid2lad.put(s, "Dudley");
            s = 305;
            ladid2lad.put(s, "Sandwell");
            s = 306;
            ladid2lad.put(s, "Solihull");
            s = 307;
            ladid2lad.put(s, "Walsall");
            s = 309;
            ladid2lad.put(s, "Wolverhampton");
            s = 320;
            ladid2lad.put(s, "Amber Valley");
            s = 321;
            ladid2lad.put(s, "Bolsover");
            s = 322;
            ladid2lad.put(s, "Chesterfield");
            s = 323;
            ladid2lad.put(s, "Derby");
            s = 324;
            ladid2lad.put(s, "Erewash");
            s = 325;
            ladid2lad.put(s, "High Peak");
            s = 327;
            ladid2lad.put(s, "North East Derbyshire");
            s = 328;
            ladid2lad.put(s, "South Derbyshire");
            s = 329;
            ladid2lad.put(s, "Derbyshire Dales");
            s = 340;
            ladid2lad.put(s, "Ashfield");
            s = 341;
            ladid2lad.put(s, "Bassetlaw");
            s = 342;
            ladid2lad.put(s, "Broxtowe");
            s = 343;
            ladid2lad.put(s, "Gedling");
            s = 344;
            ladid2lad.put(s, "Mansfield");
            s = 345;
            ladid2lad.put(s, "Newark and Sherwood");
            s = 346;
            ladid2lad.put(s, "Nottingham");
            s = 347;
            ladid2lad.put(s, "Rushcliffe");
            s = 350;
            ladid2lad.put(s, "Boston");
            s = 351;
            ladid2lad.put(s, "East Lindsey");
            s = 352;
            ladid2lad.put(s, "Lincoln");
            s = 353;
            ladid2lad.put(s, "North Kesteven");
            s = 354;
            ladid2lad.put(s, "South Holland");
            s = 355;
            ladid2lad.put(s, "South Kesteven");
            s = 356;
            ladid2lad.put(s, "West Lindsey");
            s = 360;
            ladid2lad.put(s, "Blaby");
            s = 361;
            ladid2lad.put(s, "Hinckley and Bosworth");
            s = 362;
            ladid2lad.put(s, "Charnwood");
            s = 363;
            ladid2lad.put(s, "Harborough");
            s = 364;
            ladid2lad.put(s, "Leicester");
            s = 365;
            ladid2lad.put(s, "Melton");
            s = 366;
            ladid2lad.put(s, "North West Leicestershire");
            s = 367;
            ladid2lad.put(s, "Oadby and Wigston");
            s = 368;
            ladid2lad.put(s, "Rutland");
            s = 380;
            ladid2lad.put(s, "Corby");
            s = 381;
            ladid2lad.put(s, "Daventry");
            s = 382;
            ladid2lad.put(s, "East Northamptonshire");
            s = 383;
            ladid2lad.put(s, "Kettering");
            s = 384;
            ladid2lad.put(s, "Northampton");
            s = 385;
            ladid2lad.put(s, "South Northamptonshire");
            s = 386;
            ladid2lad.put(s, "Wellingborough");
            s = 390;
            ladid2lad.put(s, "Cambridge");
            s = 391;
            ladid2lad.put(s, "East Cambridgeshire");
            s = 392;
            ladid2lad.put(s, "Fenland");
            s = 393;
            ladid2lad.put(s, "Huntingdonshire");
            s = 394;
            ladid2lad.put(s, "Peterborough");
            s = 395;
            ladid2lad.put(s, "South Cambridgeshire");
            s = 400;
            ladid2lad.put(s, "Breckland");
            s = 401;
            ladid2lad.put(s, "Broadland");
            s = 402;
            ladid2lad.put(s, "Great Yarmouth");
            s = 404;
            ladid2lad.put(s, "Norwich");
            s = 405;
            ladid2lad.put(s, "North Norfolk");
            s = 406;
            ladid2lad.put(s, "South Norfolk");
            s = 407;
            ladid2lad.put(s, "King's Lynn and West Norfolk");
            s = 410;
            ladid2lad.put(s, "Babergh");
            s = 411;
            ladid2lad.put(s, "Forest Heath");
            s = 412;
            ladid2lad.put(s, "Ipswich");
            s = 413;
            ladid2lad.put(s, "Mid Suffolk");
            s = 414;
            ladid2lad.put(s, "St. Edmundsbury");
            s = 415;
            ladid2lad.put(s, "Suffolk Coastal");
            s = 416;
            ladid2lad.put(s, "Waveney");
            s = 420;
            ladid2lad.put(s, "Bedford");
            s = 421;
            ladid2lad.put(s, "Luton");
            s = 422;
            ladid2lad.put(s, "Mid Bedfordshire");
            s = 423;
            ladid2lad.put(s, "South Bedfordshire");
            s = 424;
            ladid2lad.put(s, "Central Bedfordshire");
            s = 430;
            ladid2lad.put(s, "Broxbourne");
            s = 431;
            ladid2lad.put(s, "Dacorum");
            s = 432;
            ladid2lad.put(s, "East Hertfordshire");
            s = 433;
            ladid2lad.put(s, "North Hertfordshire");
            s = 434;
            ladid2lad.put(s, "St. Albans");
            s = 435;
            ladid2lad.put(s, "Stevenage");
            s = 436;
            ladid2lad.put(s, "Three Rivers");
            s = 437;
            ladid2lad.put(s, "Watford");
            s = 438;
            ladid2lad.put(s, "Welwyn Hatfield");
            s = 450;
            ladid2lad.put(s, "Basildon");
            s = 451;
            ladid2lad.put(s, "Braintree");
            s = 452;
            ladid2lad.put(s, "Brentwood");
            s = 453;
            ladid2lad.put(s, "Castle Point");
            s = 454;
            ladid2lad.put(s, "Chelmsford");
            s = 455;
            ladid2lad.put(s, "Colchester");
            s = 456;
            ladid2lad.put(s, "Epping Forest");
            s = 457;
            ladid2lad.put(s, "Harlow");
            s = 458;
            ladid2lad.put(s, "Maldon");
            s = 459;
            ladid2lad.put(s, "Rochford");
            s = 460;
            ladid2lad.put(s, "Southend-on-Sea");
            s = 461;
            ladid2lad.put(s, "Tendring");
            s = 462;
            ladid2lad.put(s, "Thurrock");
            s = 463;
            ladid2lad.put(s, "Uttlesford");
            s = 470;
            ladid2lad.put(s, "Bracknell Forest");
            s = 471;
            ladid2lad.put(s, "West Berkshire");
            s = 472;
            ladid2lad.put(s, "Reading");
            s = 473;
            ladid2lad.put(s, "Slough");
            s = 474;
            ladid2lad.put(s, "Windsor and Maidenhead");
            s = 475;
            ladid2lad.put(s, "Wokingham");
            s = 476;
            ladid2lad.put(s, "Aylesbury Vale");
            s = 477;
            ladid2lad.put(s, "South Bucks");
            s = 478;
            ladid2lad.put(s, "Chiltern");
            s = 479;
            ladid2lad.put(s, "Milton Keynes");
            s = 480;
            ladid2lad.put(s, "Wycombe");
            s = 481;
            ladid2lad.put(s, "Cherwell");
            s = 482;
            ladid2lad.put(s, "Oxford");
            s = 483;
            ladid2lad.put(s, "Vale of White Horse");
            s = 484;
            ladid2lad.put(s, "South Oxfordshire");
            s = 485;
            ladid2lad.put(s, "West Oxfordshire");
            s = 490;
            ladid2lad.put(s, "Basingstoke and Deane");
            s = 491;
            ladid2lad.put(s, "Eastleigh");
            s = 492;
            ladid2lad.put(s, "Fareham");
            s = 493;
            ladid2lad.put(s, "Gosport");
            s = 494;
            ladid2lad.put(s, "Hart");
            s = 495;
            ladid2lad.put(s, "Havant");
            s = 496;
            ladid2lad.put(s, "New Forest");
            s = 497;
            ladid2lad.put(s, "East Hampshire");
            s = 498;
            ladid2lad.put(s, "Portsmouth");
            s = 499;
            ladid2lad.put(s, "Rushmoor");
            s = 500;
            ladid2lad.put(s, "Southampton ");
            s = 501;
            ladid2lad.put(s, "Test Valley");
            s = 502;
            ladid2lad.put(s, "Winchester");
            s = 505;
            ladid2lad.put(s, "Isle of Wight");
            s = 510;
            ladid2lad.put(s, "Elmbridge");
            s = 511;
            ladid2lad.put(s, "Guildford");
            s = 512;
            ladid2lad.put(s, "Mole Valley");
            s = 513;
            ladid2lad.put(s, "Reigate and Banstead");
            s = 514;
            ladid2lad.put(s, "Runnymede");
            s = 515;
            ladid2lad.put(s, "Surrey Heath");
            s = 516;
            ladid2lad.put(s, "Tandridge");
            s = 517;
            ladid2lad.put(s, "Waverley");
            s = 518;
            ladid2lad.put(s, "Woking");
            s = 530;
            ladid2lad.put(s, "Ashford");
            s = 531;
            ladid2lad.put(s, "Canterbury");
            s = 532;
            ladid2lad.put(s, "Dartford");
            s = 533;
            ladid2lad.put(s, "Dover");
            s = 535;
            ladid2lad.put(s, "Gravesham");
            s = 536;
            ladid2lad.put(s, "Maidstone");
            s = 538;
            ladid2lad.put(s, "Sevenoaks");
            s = 539;
            ladid2lad.put(s, "Shepway");
            s = 540;
            ladid2lad.put(s, "Swale");
            s = 541;
            ladid2lad.put(s, "Thanet");
            s = 542;
            ladid2lad.put(s, "Tonbridge and Malling");
            s = 543;
            ladid2lad.put(s, "Tunbridge Wells");
            s = 544;
            ladid2lad.put(s, "Medway");
            s = 551;
            ladid2lad.put(s, "Eastbourne");
            s = 552;
            ladid2lad.put(s, "Hastings");
            s = 554;
            ladid2lad.put(s, "Lewes");
            s = 555;
            ladid2lad.put(s, "Rother");
            s = 556;
            ladid2lad.put(s, "Wealden");
            s = 557;
            ladid2lad.put(s, "Adur");
            s = 558;
            ladid2lad.put(s, "Arun");
            s = 559;
            ladid2lad.put(s, "Chichester");
            s = 560;
            ladid2lad.put(s, "Crawley");
            s = 562;
            ladid2lad.put(s, "Horsham");
            s = 563;
            ladid2lad.put(s, "Mid Sussex");
            s = 564;
            ladid2lad.put(s, "Worthing");
            s = 565;
            ladid2lad.put(s, "Brighton and Hove");
            s = 570;
            ladid2lad.put(s, "City of London");
            s = 580;
            ladid2lad.put(s, "East Devon");
            s = 581;
            ladid2lad.put(s, "Exeter");
            s = 582;
            ladid2lad.put(s, "North Devon");
            s = 583;
            ladid2lad.put(s, "Plymouth");
            s = 584;
            ladid2lad.put(s, "South Hams");
            s = 585;
            ladid2lad.put(s, "Teignbridge");
            s = 586;
            ladid2lad.put(s, "Mid Devon");
            s = 587;
            ladid2lad.put(s, "Torbay");
            s = 588;
            ladid2lad.put(s, "Torridge");
            s = 589;
            ladid2lad.put(s, "West Devon");
            s = 590;
            ladid2lad.put(s, "Caradon");
            s = 591;
            ladid2lad.put(s, "Carrick");
            s = 592;
            ladid2lad.put(s, "Kerrier");
            s = 593;
            ladid2lad.put(s, "North Cornwall");
            s = 594;
            ladid2lad.put(s, "Penwith");
            s = 595;
            ladid2lad.put(s, "Restormel");
            s = 596;
            ladid2lad.put(s, "Cornwall");
            s = 601;
            ladid2lad.put(s, "Bristol");
            s = 605;
            ladid2lad.put(s, "North Somerset");
            s = 606;
            ladid2lad.put(s, "Mendip");
            s = 607;
            ladid2lad.put(s, "Sedgemoor");
            s = 608;
            ladid2lad.put(s, "Taunton Deane");
            s = 609;
            ladid2lad.put(s, "West Somerset");
            s = 610;
            ladid2lad.put(s, "South Somerset");
            s = 611;
            ladid2lad.put(s, "Bath and North East Somerset");
            s = 612;
            ladid2lad.put(s, "South Gloucestershire");
            s = 620;
            ladid2lad.put(s, "Cheltenham");
            s = 621;
            ladid2lad.put(s, "Cotswold");
            s = 622;
            ladid2lad.put(s, "Forest of Dean");
            s = 623;
            ladid2lad.put(s, "Gloucester");
            s = 624;
            ladid2lad.put(s, "Stroud");
            s = 625;
            ladid2lad.put(s, "Tewkesbury");
            s = 630;
            ladid2lad.put(s, "Kennet");
            s = 631;
            ladid2lad.put(s, "North Wiltshire");
            s = 632;
            ladid2lad.put(s, "Salisbury");
            s = 633;
            ladid2lad.put(s, "Swindon");
            s = 634;
            ladid2lad.put(s, "West Wiltshire");
            s = 635;
            ladid2lad.put(s, "Wiltshire");
            s = 640;
            ladid2lad.put(s, "Bournemouth");
            s = 641;
            ladid2lad.put(s, "Christchurch");
            s = 642;
            ladid2lad.put(s, "North Dorset");
            s = 643;
            ladid2lad.put(s, "Poole");
            s = 644;
            ladid2lad.put(s, "Purbeck");
            s = 645;
            ladid2lad.put(s, "West Dorset");
            s = 646;
            ladid2lad.put(s, "Weymouth and Portland");
            s = 647;
            ladid2lad.put(s, "East Dorset");
            s = 720;
            ladid2lad.put(s, "Isle of Anglesey");
            s = 721;
            ladid2lad.put(s, "Conwy");
            s = 722;
            ladid2lad.put(s, "Gwynedd");
            s = 723;
            ladid2lad.put(s, "Denbighshire");
            s = 724;
            ladid2lad.put(s, "Flintshire");
            s = 725;
            ladid2lad.put(s, "Wrexham");
            s = 730;
            ladid2lad.put(s, "Blaenau Gwent");
            s = 731;
            ladid2lad.put(s, "Caerphilly");
            s = 732;
            ladid2lad.put(s, "Monmouthshire");
            s = 733;
            ladid2lad.put(s, "Newport");
            s = 734;
            ladid2lad.put(s, "Torfaen");
            s = 740;
            ladid2lad.put(s, "Bridgend");
            s = 741;
            ladid2lad.put(s, "Cardiff");
            s = 742;
            ladid2lad.put(s, "Merthyr Tydfil");
            s = 743;
            ladid2lad.put(s, "Neath Port Talbot");
            s = 744;
            ladid2lad.put(s, "Rhondda");
            s = 745;
            ladid2lad.put(s, "Swansea");
            s = 746;
            ladid2lad.put(s, "The Vale of Glamorgan");
            s = 750;
            ladid2lad.put(s, "Ceredigion");
            s = 751;
            ladid2lad.put(s, "Carmarthenshire");
            s = 752;
            ladid2lad.put(s, "Pembrokeshire");
            s = 753;
            ladid2lad.put(s, "Powys");
            s = 910;
            ladid2lad.put(s, "Aberdeen City");
            s = 911;
            ladid2lad.put(s, "Aberdeenshire");
            s = 912;
            ladid2lad.put(s, "Angus");
            s = 913;
            ladid2lad.put(s, "Argyll and Bute");
            s = 914;
            ladid2lad.put(s, "Scottish Borders");
            s = 915;
            ladid2lad.put(s, "Clackmannanshire");
            s = 916;
            ladid2lad.put(s, "West Dunbartonshire");
            s = 917;
            ladid2lad.put(s, "Dumfries and Galloway");
            s = 918;
            ladid2lad.put(s, "Dundee City");
            s = 919;
            ladid2lad.put(s, "East Ayrshire");
            s = 920;
            ladid2lad.put(s, "East Dunbartonshire");
            s = 921;
            ladid2lad.put(s, "East Lothian");
            s = 922;
            ladid2lad.put(s, "East Renfrewshire");
            s = 923;
            ladid2lad.put(s, "Edinburgh");
            s = 924;
            ladid2lad.put(s, "Falkirk");
            s = 925;
            ladid2lad.put(s, "Fife");
            s = 926;
            ladid2lad.put(s, "Glasgow City");
            s = 927;
            ladid2lad.put(s, "Highland");
            s = 928;
            ladid2lad.put(s, "Inverclyde");
            s = 929;
            ladid2lad.put(s, "Midlothian");
            s = 930;
            ladid2lad.put(s, "Moray");
            s = 931;
            ladid2lad.put(s, "North Ayrshire");
            s = 932;
            ladid2lad.put(s, "North Lanarkshire");
            s = 933;
            ladid2lad.put(s, "Orkney Islands");
            s = 934;
            ladid2lad.put(s, "Perth and Kinross");
            s = 935;
            ladid2lad.put(s, "Renfrewshire");
            s = 936;
            ladid2lad.put(s, "Shetland Islands");
            s = 937;
            ladid2lad.put(s, "South Ayrshire");
            s = 938;
            ladid2lad.put(s, "South Lanarkshire");
            s = 939;
            ladid2lad.put(s, "Stirling");
            s = 940;
            ladid2lad.put(s, "West Lothian");
            s = 941;
            ladid2lad.put(s, "Western Isles");
        }
        return ladid2lad;
    }

    /**
     * Local Authority Highway
     * <ul>
     * <li>code,label</li>
     * <li>S12000033,Aberdeen City</li>
     * <li>S12000034,Aberdeenshire</li>
     * <li>S12000041,Angus</li>
     * <li>S12000035,Argyll & Bute</li>
     * <li>E09000002,Barking and Dagenham</li>
     * <li>E09000003,Barnet</li>
     * <li>E08000016,Barnsley</li>
     * <li>E06000022,Bath and North East Somerset</li>
     * <li>E06000055,Bedford</li>
     * <li>E09000004,Bexley</li>
     * <li>E08000025,Birmingham</li>
     * <li>E06000008,Blackburn with Darwen</li>
     * <li>E06000009,Blackpool</li>
     * <li>W06000019,Blaenau Gwent</li>
     * <li>E08000001,Bolton</li>
     * <li>E06000028,Bournemouth</li>
     * <li>E06000036,Bracknell Forest</li>
     * <li>E08000032,Bradford</li>
     * <li>E09000005,Brent</li>
     * <li>W06000013,Bridgend</li>
     * <li>E06000043,Brighton and Hove</li>
     * <li>E06000023,"Bristol, City of"</li>
     * <li>E09000006,Bromley</li>
     * <li>E10000002,Buckinghamshire</li>
     * <li>E08000002,Bury</li>
     * <li>W06000018,Caerphilly</li>
     * <li>E08000033,Calderdale</li>
     * <li>E10000003,Cambridgeshire</li>
     * <li>E09000007,Camden</li>
     * <li>W06000015,Cardiff</li>
     * <li>W06000010,Carmarthenshire</li>
     * <li>E06000056,Central Bedfordshire</li>
     * <li>W06000008,Ceredigion</li>
     * <li>E06000049,Cheshire East</li>
     * <li>E06000050,Cheshire West and Chester</li>
     * <li>E09000001,City of London</li>
     * <li>S12000005,Clackmannanshire</li>
     * <li>W06000003,Conwy</li>
     * <li>E06000052,Cornwall</li>
     * <li>E06000047,County Durham</li>
     * <li>E08000026,Coventry</li>
     * <li>E09000008,Croydon</li>
     * <li>E10000006,Cumbria</li>
     * <li>E06000005,Darlington</li>
     * <li>W06000004,Denbighshire</li>
     * <li>E06000015,Derby</li>
     * <li>E10000007,Derbyshire</li>
     * <li>E10000008,Devon</li>
     * <li>E08000017,Doncaster</li>
     * <li>E10000009,Dorset</li>
     * <li>E08000027,Dudley</li>
     * <li>S12000006,Dumfries & Galloway</li>
     * <li>S12000042,Dundee City</li>
     * <li>E09000009,Ealing</li>
     * <li>S12000008,East Ayrshire</li>
     * <li>S12000009,East Dunbartonshire</li>
     * <li>S12000010,East Lothian</li>
     * <li>S12000011,East Renfrewshire</li>
     * <li>E06000011,East Riding of Yorkshire</li>
     * <li>E10000011,East Sussex</li>
     * <li>S12000036,"Edinburgh, City of"</li>
     * <li>E09000010,Enfield</li>
     * <li>E10000012,Essex</li>
     * <li>S12000014,Falkirk</li>
     * <li>S12000015,Fife</li>
     * <li>W06000005,Flintshire</li>
     * <li>E08000020,Gateshead</li>
     * <li>S12000043,Glasgow City</li>
     * <li>E10000013,Gloucestershire</li>
     * <li>E09000011,Greenwich</li>
     * <li>W06000002,Gwynedd</li>
     * <li>E09000012,Hackney</li>
     * <li>E06000006,Halton</li>
     * <li>E09000013,Hammersmith and Fulham</li>
     * <li>E10000014,Hampshire</li>
     * <li>E09000014,Haringey</li>
     * <li>E09000015,Harrow</li>
     * <li>E06000001,Hartlepool</li>
     * <li>E09000016,Havering</li>
     * <li>E06000019,"Herefordshire, County of "</li>
     * <li>E10000015,Hertfordshire</li>
     * <li>S12000017,Highland</li>
     * <li>E09000017,Hillingdon</li>
     * <li>E09000018,Hounslow</li>
     * <li>S12000018,Inverclyde</li>
     * <li>W06000001,Isle of Anglesey</li>
     * <li>E06000046,Isle of Wight</li>
     * <li>E06000053,Isles of Scilly</li>
     * <li>E09000019,Islington</li>
     * <li>E09000020,Kensington and Chelsea</li>
     * <li>E10000016,Kent</li>
     * <li>E06000010,"Kingston upon Hull, City of"</li>
     * <li>E09000021,Kingston upon Thames</li>
     * <li>E08000034,Kirklees</li>
     * <li>E08000011,Knowsley</li>
     * <li>E09000022,Lambeth</li>
     * <li>E10000017,Lancashire</li>
     * <li>E08000035,Leeds</li>
     * <li>E06000016,Leicester</li>
     * <li>E10000018,Leicestershire</li>
     * <li>E09000023,Lewisham</li>
     * <li>E10000019,Lincolnshire</li>
     * <li>E08000012,Liverpool</li>
     * <li>EHEATHROW,London Airport (Heathrow)</li>
     * <li>E06000032,Luton</li>
     * <li>E08000003,Manchester</li>
     * <li>E06000035,Medway</li>
     * <li>W06000024,Merthyr Tydfil</li>
     * <li>E09000024,Merton</li>
     * <li>E06000002,Middlesbrough</li>
     * <li>S12000019,Midlothian</li>
     * <li>E06000042,Milton Keynes</li>
     * <li>W06000021,Monmouthshire</li>
     * <li>S12000020,Moray</li>
     * <li>S12000013,Na h-Eileanan an Iar (Western Isles)</li>
     * <li>W06000012,Neath Port Talbot</li>
     * <li>E08000021,Newcastle upon Tyne</li>
     * <li>E09000025,Newham</li>
     * <li>W06000022,Newport</li>
     * <li>E10000020,Norfolk</li>
     * <li>S12000021,North Ayrshire</li>
     * <li>E06000012,North East Lincolnshire</li>
     * <li>S12000044,North Lanarkshire</li>
     * <li>E06000013,North Lincolnshire</li>
     * <li>E06000024,North Somerset</li>
     * <li>E08000022,North Tyneside</li>
     * <li>E10000023,North Yorkshire</li>
     * <li>E10000021,Northamptonshire</li>
     * <li>E06000048,Northumberland</li>
     * <li>E06000018,Nottingham</li>
     * <li>E10000024,Nottinghamshire</li>
     * <li>E08000004,Oldham</li>
     * <li>S12000023,Orkney Islands</li>
     * <li>E10000025,Oxfordshire</li>
     * <li>W06000009,Pembrokeshire</li>
     * <li>S12000024,Perth and Kinross</li>
     * <li>E06000031,Peterborough</li>
     * <li>E06000026,Plymouth</li>
     * <li>E06000029,Poole</li>
     * <li>E06000044,Portsmouth</li>
     * <li>W06000023,Powys</li>
     * <li>E06000038,Reading</li>
     * <li>E09000026,Redbridge</li>
     * <li>E06000003,Redcar and Cleveland</li>
     * <li>S12000038,Renfrewshire</li>
     * <li>W06000016,"Rhondda, Cynon, Taff"</li>
     * <li>E09000027,Richmond upon Thames</li>
     * <li>E08000005,Rochdale</li>
     * <li>E08000018,Rotherham</li>
     * <li>E06000017,Rutland</li>
     * <li>E08000006,Salford</li>
     * <li>E08000028,Sandwell</li>
     * <li>S12000026,Scottish Borders</li>
     * <li>E08000014,Sefton</li>
     * <li>E08000019,Sheffield</li>
     * <li>S12000027,Shetland Islands</li>
     * <li>E06000051,Shropshire</li>
     * <li>E06000039,Slough</li>
     * <li>E08000029,Solihull</li>
     * <li>E10000027,Somerset</li>
     * <li>S12000028,South Ayrshire</li>
     * <li>E06000025,South Gloucestershire</li>
     * <li>S12000029,South Lanarkshire</li>
     * <li>E08000023,South Tyneside</li>
     * <li>E06000045,Southampton </li>
     * <li>E06000033,Southend-on-Sea</li>
     * <li>E09000028,Southwark</li>
     * <li>E08000013,St. Helens</li>
     * <li>E10000028,Staffordshire</li>
     * <li>S12000030,Stirling</li>
     * <li>E08000007,Stockport</li>
     * <li>E06000004,Stockton-on-Tees</li>
     * <li>E06000021,Stoke-on-Trent</li>
     * <li>E10000029,Suffolk</li>
     * <li>E08000024,Sunderland</li>
     * <li>E10000030,Surrey</li>
     * <li>E09000029,Sutton</li>
     * <li>W06000011,Swansea</li>
     * <li>E06000030,Swindon</li>
     * <li>E08000008,Tameside</li>
     * <li>E06000020,Telford and Wrekin</li>
     * <li>W06000014,The Vale of Glamorgan</li>
     * <li>E06000034,Thurrock</li>
     * <li>E06000027,Torbay</li>
     * <li>W06000020,Torfaen</li>
     * <li>E09000030,Tower Hamlets</li>
     * <li>E08000009,Trafford</li>
     * <li>E08000036,Wakefield</li>
     * <li>E08000030,Walsall</li>
     * <li>E09000031,Waltham Forest</li>
     * <li>E09000032,Wandsworth</li>
     * <li>E06000007,Warrington</li>
     * <li>E10000031,Warwickshire</li>
     * <li>E06000037,West Berkshire</li>
     * <li>S12000039,West Dunbartonshire</li>
     * <li>S12000040,West Lothian</li>
     * <li>E10000032,West Sussex</li>
     * <li>E09000033,Westminster</li>
     * <li>E08000010,Wigan</li>
     * <li>E06000054,Wiltshire</li>
     * <li>E06000040,Windsor and Maidenhead</li>
     * <li>E08000015,Wirral</li>
     * <li>E06000041,Wokingham</li>
     * <li>E08000031,Wolverhampton</li>
     * <li>E10000034,Worcestershire</li>
     * <li>W06000006,Wrexham</li>
     * <li>E06000014,York</li>
     * </ul>
     */
    HashMap<String, String> lahid2lah;

    /**
     * @return {@link #lahid2lah} initialised first if it is {@code null}.
     */
    public HashMap<String, String> getLahid2lah() {
        if (lahid2lah == null) {
            lahid2lah = new HashMap<>();
            lahid2lah.put("S12000033", "Aberdeen City");
            lahid2lah.put("S12000034", "Aberdeenshire");
            lahid2lah.put("S12000041", "Angus");
            lahid2lah.put("S12000035", "Argyll & Bute");
            lahid2lah.put("E09000002", "Barking and Dagenham");
            lahid2lah.put("E09000003", "Barnet");
            lahid2lah.put("E08000016", "Barnsley");
            lahid2lah.put("E06000022", "Bath and North East Somerset");
            lahid2lah.put("E06000055", "Bedford");
            lahid2lah.put("E09000004", "Bexley");
            lahid2lah.put("E08000025", "Birmingham");
            lahid2lah.put("E06000008", "Blackburn with Darwen");
            lahid2lah.put("E06000009", "Blackpool");
            lahid2lah.put("W06000019", "Blaenau Gwent");
            lahid2lah.put("E08000001", "Bolton");
            lahid2lah.put("E06000028", "Bournemouth");
            lahid2lah.put("E06000036", "Bracknell Forest");
            lahid2lah.put("E08000032", "Bradford");
            lahid2lah.put("E09000005", "Brent");
            lahid2lah.put("W06000013", "Bridgend");
            lahid2lah.put("E06000043", "Brighton and Hove");
            lahid2lah.put("E06000023", "Bristol");
            lahid2lah.put("E09000006", "Bromley");
            lahid2lah.put("E10000002", "Buckinghamshire");
            lahid2lah.put("E08000002", "Bury");
            lahid2lah.put("W06000018", "Caerphilly");
            lahid2lah.put("E08000033", "Calderdale");
            lahid2lah.put("E10000003", "Cambridgeshire");
            lahid2lah.put("E09000007", "Camden");
            lahid2lah.put("W06000015", "Cardiff");
            lahid2lah.put("W06000010", "Carmarthenshire");
            lahid2lah.put("E06000056", "Central Bedfordshire");
            lahid2lah.put("W06000008", "Ceredigion");
            lahid2lah.put("E06000049", "Cheshire East");
            lahid2lah.put("E06000050", "Cheshire West and Chester");
            lahid2lah.put("E09000001", "City of London");
            lahid2lah.put("S12000005", "Clackmannanshire");
            lahid2lah.put("W06000003", "Conwy");
            lahid2lah.put("E06000052", "Cornwall");
            lahid2lah.put("E06000047", "County Durham");
            lahid2lah.put("E08000026", "Coventry");
            lahid2lah.put("E09000008", "Croydon");
            lahid2lah.put("E10000006", "Cumbria");
            lahid2lah.put("E06000005", "Darlington");
            lahid2lah.put("W06000004", "Denbighshire");
            lahid2lah.put("E06000015", "Derby");
            lahid2lah.put("E10000007", "Derbyshire");
            lahid2lah.put("E10000008", "Devon");
            lahid2lah.put("E08000017", "Doncaster");
            lahid2lah.put("E10000009", "Dorset");
            lahid2lah.put("E08000027", "Dudley");
            lahid2lah.put("S12000006", "Dumfries & Galloway");
            lahid2lah.put("S12000042", "Dundee City");
            lahid2lah.put("E09000009", "Ealing");
            lahid2lah.put("S12000008", "East Ayrshire");
            lahid2lah.put("S12000009", "East Dunbartonshire");
            lahid2lah.put("S12000010", "East Lothian");
            lahid2lah.put("S12000011", "East Renfrewshire");
            lahid2lah.put("E06000011", "East Riding of Yorkshire");
            lahid2lah.put("E10000011", "East Sussex");
            lahid2lah.put("S12000036", "Edinburgh");
            lahid2lah.put("E09000010", "Enfield");
            lahid2lah.put("E10000012", "Essex");
            lahid2lah.put("S12000014", "Falkirk");
            lahid2lah.put("S12000015", "Fife");
            lahid2lah.put("W06000005", "Flintshire");
            lahid2lah.put("E08000020", "Gateshead");
            lahid2lah.put("S12000043", "Glasgow City");
            lahid2lah.put("E10000013", "Gloucestershire");
            lahid2lah.put("E09000011", "Greenwich");
            lahid2lah.put("W06000002", "Gwynedd");
            lahid2lah.put("E09000012", "Hackney");
            lahid2lah.put("E06000006", "Halton");
            lahid2lah.put("E09000013", "Hammersmith and Fulham");
            lahid2lah.put("E10000014", "Hampshire");
            lahid2lah.put("E09000014", "Haringey");
            lahid2lah.put("E09000015", "Harrow");
            lahid2lah.put("E06000001", "Hartlepool");
            lahid2lah.put("E09000016", "Havering");
            lahid2lah.put("E06000019", "Herefordshire");
            lahid2lah.put("E10000015", "Hertfordshire");
            lahid2lah.put("S12000017", "Highland");
            lahid2lah.put("E09000017", "Hillingdon");
            lahid2lah.put("E09000018", "Hounslow");
            lahid2lah.put("S12000018", "Inverclyde");
            lahid2lah.put("W06000001", "Isle of Anglesey");
            lahid2lah.put("E06000046", "Isle of Wight");
            lahid2lah.put("E06000053", "Isles of Scilly");
            lahid2lah.put("E09000019", "Islington");
            lahid2lah.put("E09000020", "Kensington and Chelsea");
            lahid2lah.put("E10000016", "Kent");
            lahid2lah.put("E06000010", "Kingston upon Hull");
            lahid2lah.put("E09000021", "Kingston upon Thames");
            lahid2lah.put("E08000034", "Kirklees");
            lahid2lah.put("E08000011", "Knowsley");
            lahid2lah.put("E09000022", "Lambeth");
            lahid2lah.put("E10000017", "Lancashire");
            lahid2lah.put("E08000035", "Leeds");
            lahid2lah.put("E06000016", "Leicester");
            lahid2lah.put("E10000018", "Leicestershire");
            lahid2lah.put("E09000023", "Lewisham");
            lahid2lah.put("E10000019", "Lincolnshire");
            lahid2lah.put("E08000012", "Liverpool");
            lahid2lah.put("EHEATHROW", "London Airport (Heathrow)");
            lahid2lah.put("E06000032", "Luton");
            lahid2lah.put("E08000003", "Manchester");
            lahid2lah.put("E06000035", "Medway");
            lahid2lah.put("W06000024", "Merthyr Tydfil");
            lahid2lah.put("E09000024", "Merton");
            lahid2lah.put("E06000002", "Middlesbrough");
            lahid2lah.put("S12000019", "Midlothian");
            lahid2lah.put("E06000042", "Milton Keynes");
            lahid2lah.put("W06000021", "Monmouthshire");
            lahid2lah.put("S12000020", "Moray");
            lahid2lah.put("S12000013", "Na h-Eileanan an Iar (Western Isles)");
            lahid2lah.put("W06000012", "Neath Port Talbot");
            lahid2lah.put("E08000021", "Newcastle upon Tyne");
            lahid2lah.put("E09000025", "Newham");
            lahid2lah.put("W06000022", "Newport");
            lahid2lah.put("E10000020", "Norfolk");
            lahid2lah.put("S12000021", "North Ayrshire");
            lahid2lah.put("E06000012", "North East Lincolnshire");
            lahid2lah.put("S12000044", "North Lanarkshire");
            lahid2lah.put("E06000013", "North Lincolnshire");
            lahid2lah.put("E06000024", "North Somerset");
            lahid2lah.put("E08000022", "North Tyneside");
            lahid2lah.put("E10000023", "North Yorkshire");
            lahid2lah.put("E10000021", "Northamptonshire");
            lahid2lah.put("E06000048", "Northumberland");
            lahid2lah.put("E06000018", "Nottingham");
            lahid2lah.put("E10000024", "Nottinghamshire");
            lahid2lah.put("E08000004", "Oldham");
            lahid2lah.put("S12000023", "Orkney Islands");
            lahid2lah.put("E10000025", "Oxfordshire");
            lahid2lah.put("W06000009", "Pembrokeshire");
            lahid2lah.put("S12000024", "Perth and Kinross");
            lahid2lah.put("E06000031", "Peterborough");
            lahid2lah.put("E06000026", "Plymouth");
            lahid2lah.put("E06000029", "Poole");
            lahid2lah.put("E06000044", "Portsmouth");
            lahid2lah.put("W06000023", "Powys");
            lahid2lah.put("E06000038", "Reading");
            lahid2lah.put("E09000026", "Redbridge");
            lahid2lah.put("E06000003", "Redcar and Cleveland");
            lahid2lah.put("S12000038", "Renfrewshire");
            lahid2lah.put("W06000016", "Rhondda");
            lahid2lah.put("E09000027", "Richmond upon Thames");
            lahid2lah.put("E08000005", "Rochdale");
            lahid2lah.put("E08000018", "Rotherham");
            lahid2lah.put("E06000017", "Rutland");
            lahid2lah.put("E08000006", "Salford");
            lahid2lah.put("E08000028", "Sandwell");
            lahid2lah.put("S12000026", "Scottish Borders");
            lahid2lah.put("E08000014", "Sefton");
            lahid2lah.put("E08000019", "Sheffield");
            lahid2lah.put("S12000027", "Shetland Islands");
            lahid2lah.put("E06000051", "Shropshire");
            lahid2lah.put("E06000039", "Slough");
            lahid2lah.put("E08000029", "Solihull");
            lahid2lah.put("E10000027", "Somerset");
            lahid2lah.put("S12000028", "South Ayrshire");
            lahid2lah.put("E06000025", "South Gloucestershire");
            lahid2lah.put("S12000029", "South Lanarkshire");
            lahid2lah.put("E08000023", "South Tyneside");
            lahid2lah.put("E06000045", "Southampton ");
            lahid2lah.put("E06000033", "Southend-on-Sea");
            lahid2lah.put("E09000028", "Southwark");
            lahid2lah.put("E08000013", "St. Helens");
            lahid2lah.put("E10000028", "Staffordshire");
            lahid2lah.put("S12000030", "Stirling");
            lahid2lah.put("E08000007", "Stockport");
            lahid2lah.put("E06000004", "Stockton-on-Tees");
            lahid2lah.put("E06000021", "Stoke-on-Trent");
            lahid2lah.put("E10000029", "Suffolk");
            lahid2lah.put("E08000024", "Sunderland");
            lahid2lah.put("E10000030", "Surrey");
            lahid2lah.put("E09000029", "Sutton");
            lahid2lah.put("W06000011", "Swansea");
            lahid2lah.put("E06000030", "Swindon");
            lahid2lah.put("E08000008", "Tameside");
            lahid2lah.put("E06000020", "Telford and Wrekin");
            lahid2lah.put("W06000014", "The Vale of Glamorgan");
            lahid2lah.put("E06000034", "Thurrock");
            lahid2lah.put("E06000027", "Torbay");
            lahid2lah.put("W06000020", "Torfaen");
            lahid2lah.put("E09000030", "Tower Hamlets");
            lahid2lah.put("E08000009", "Trafford");
            lahid2lah.put("E08000036", "Wakefield");
            lahid2lah.put("E08000030", "Walsall");
            lahid2lah.put("E09000031", "Waltham Forest");
            lahid2lah.put("E09000032", "Wandsworth");
            lahid2lah.put("E06000007", "Warrington");
            lahid2lah.put("E10000031", "Warwickshire");
            lahid2lah.put("E06000037", "West Berkshire");
            lahid2lah.put("S12000039", "West Dunbartonshire");
            lahid2lah.put("S12000040", "West Lothian");
            lahid2lah.put("E10000032", "West Sussex");
            lahid2lah.put("E09000033", "Westminster");
            lahid2lah.put("E08000010", "Wigan");
            lahid2lah.put("E06000054", "Wiltshire");
            lahid2lah.put("E06000040", "Windsor and Maidenhead");
            lahid2lah.put("E08000015", "Wirral");
            lahid2lah.put("E06000041", "Wokingham");
            lahid2lah.put("E08000031", "Wolverhampton");
            lahid2lah.put("E10000034", "Worcestershire");
            lahid2lah.put("W06000006", "Wrexham");
            lahid2lah.put("E06000014", "York");
        }
        return lahid2lah;
    }

    /**
     * Age Band
     * <ul>
     * <li>code,label</li>
     * <li>1,0 - 5</li>
     * <li>2,6 - 10</li>
     * <li>3,11 - 15</li>
     * <li>4,16 - 20</li>
     * <li>5,21 - 25</li>
     * <li>6,26 - 35</li>
     * <li>7,36 - 45</li>
     * <li>8,46 - 55</li>
     * <li>9,56 - 65</li>
     * <li>10,66 - 75</li>
     * <li>11,Over 75</li>
     * </ul>
     */
    HashMap<Byte, String> abid2ab;

    /**
     * @return {@link #abid2ab} initialised first if it is {@code null}.
     */
    public HashMap<Byte, String> getAbid2ab() {
        if (abid2ab == null) {
            abid2ab = new HashMap<>();
            byte b = 1;
            abid2ab.put(b, "0 - 5");
            b = 2;
            abid2ab.put(b, "6 - 10");
            b = 3;
            abid2ab.put(b, "11 - 15");
            b = 4;
            abid2ab.put(b, "16 - 20");
            b = 5;
            abid2ab.put(b, "21 - 25");
            b = 6;
            abid2ab.put(b, "26 - 35");
            b = 7;
            abid2ab.put(b, "36 - 45");
            b = 8;
            abid2ab.put(b, "46 - 55");
            b = 9;
            abid2ab.put(b, "56 - 65");
            b = 10;
            abid2ab.put(b, "66 - 75");
            b = 11;
            abid2ab.put(b, "Over 75");
        }
        return abid2ab;
    }

    /**
     *
     * @param cid
     * @return
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public F_Collection getCollection(F_CollectionID cid)
            throws IOException, ClassNotFoundException {
        F_Collection r = data.get(cid);
        if (r == null) {
            r = loadCollection(cid);
            data.put(cid, r);
        }
        return r;
    }

    public void clearCollection(F_CollectionID cid) {
        data.put(cid, null);
    }

    public F_Data(F_Environment se) throws IOException, Exception {
        super(se);
        String name = "Collections";
        Path dir = se.files.getGeneratedDir();
        Path p = Paths.get(dir.toString(), name);
        if (Files.exists(p)) {
            fs = new Generic_FileStore(p);
        } else {
            short n = 100;
            fs = new Generic_FileStore(p.getParent(), name, n);
        }
        data = new HashMap<>();
        cID2recIDs = new HashMap<>();
        recID2cID = new HashMap<>();
    }

    public boolean clearSomeData() throws IOException {
        Iterator<F_CollectionID> ite = data.keySet().iterator();
        while (ite.hasNext()) {
            F_CollectionID cid = ite.next();
            F_Collection c = data.get(cid);
            cacheCollection(cid, c);
            data.put(cid, null);
            return true;
        }
        return false;
    }

    public int clearAllData() throws IOException {
        int r = 0;
        Iterator<F_CollectionID> ite = data.keySet().iterator();
        while (ite.hasNext()) {
            F_CollectionID cid = ite.next();
            F_Collection c = data.get(cid);
            cacheCollection(cid, c);
            data.put(cid, null);
            r++;
        }
        return r;
    }

    /**
     *
     * @param cid the F_CollectionID
     * @param c the F_Collection
     * @throws java.io.IOException
     */
    public void cacheCollection(F_CollectionID cid, F_Collection c)
            throws IOException {
        cache(getCollectionPath(cid), c);
    }

    public Path getCollectionPath(F_CollectionID cid) throws IOException {
        return Paths.get(fs.getPath(cid.id).toString(), F_Strings.s_F
                + cid.id + F_Strings.symbol_dot + F_Strings.s_dat);
    }

    /**
     *
     * @param cid The F_CollectionID
     * @return
     * @throws java.io.IOException
     * @throws java.lang.ClassNotFoundException
     */
    public F_Collection loadCollection(F_CollectionID cid) throws IOException,
            ClassNotFoundException {
        F_Collection r = (F_Collection) load(getCollectionPath(cid));
        r.env = env;
        return r;
    }

    /**
     *
     * @param f the Path to load from.
     * @return
     * @throws java.io.IOException If encountered.
     * @throws java.lang.ClassNotFoundException If encountered.
     */
    protected Object load(Path f) throws IOException, ClassNotFoundException {
        String m = "load " + f.toString();
        env.logStartTag(m);
        Object r = Generic_IO.readObject(f);
        env.logEndTag(m);
        return r;
    }

    /**
     *
     * @throws java.io.IOException
     */
    public void swapCollections() throws IOException {
        data.keySet().stream().forEach(i -> {
            try {
                F_Collection c = data.get(i);
                if (c != null) {
                    cacheCollection(i, data.get(i));
                    data.put(i, null);
                }
            } catch (IOException ex) {
                throw new RuntimeException(ex.getMessage());
            }
        });
//        Iterator<F_CollectionID> ite = data.keySet().iterator();
//        while (ite.hasNext()) {
//            F_CollectionID cid = ite.next();
//            F_Collection c = data.get(cid);
//            cacheCollection(cid, c);
//            data.put(cid, null);
//        }
    }

    /**
     *
     * @param f the value of cf
     * @param o the value of o
     * @throws java.io.IOException If encountered.
     */
    protected void cache(Path f, Object o) throws IOException {
        String m = "cache " + f.toString();
        env.logStartTag(m);
        Files.createDirectories(f.getParent());
        Generic_IO.writeObject(o, f);
        env.logEndTag(m);
    }

}

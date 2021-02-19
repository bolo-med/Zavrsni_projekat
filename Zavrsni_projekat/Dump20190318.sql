-- MySQL dump 10.13  Distrib 5.7.25, for Linux (x86_64)
--
-- Host: localhost    Database: Kucna_biblioteka
-- ------------------------------------------------------
-- Server version	5.7.25-0ubuntu0.16.04.2

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `AUTOR`
--

DROP TABLE IF EXISTS `AUTOR`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `AUTOR` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `Ime_prezime` varchar(50) NOT NULL,
  `Biografija` varchar(1000) DEFAULT NULL,
  `Slika` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`Id`),
  UNIQUE KEY `Ime_prezime_UNIQUE` (`Ime_prezime`)
) ENGINE=InnoDB AUTO_INCREMENT=41 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `AUTOR`
--

LOCK TABLES `AUTOR` WRITE;
/*!40000 ALTER TABLE `AUTOR` DISABLE KEYS */;
INSERT INTO `AUTOR` VALUES (1,'Mark Minasi','Mark Minasi (Directory Services MVP) je jedan od vodecih svetskih strucnjaka za operativni sistem Windows. Mark drzi predavanja u nekoliko desetina drzava i ima izlaganja na konferecijama i skupovima. Njegova firma MR&D je obucila desetine hiljada ljudi za projektovanje i koriscenje Windows racunarskih mreza. Mark je napisao vise od 25 knjiga za izdavacku kucu Sybex, ukljucujuci vrhunske knjige „Mastering Windows Server 2003“ i „The Complete PC Upgrade and Maintenance Guide“.','src/slike_a/1.jpg'),(2,'Fjodor M. Dostojevski','Fjodor Mihajlovic Dostojevski je bio jedan od najvecih pisaca svih vremena. Roden je 11. novembra 1821. godine u Moskvi. Preminuo je 9. februara, 1881. godine u Sankt Peterburgu.\n\nZavrsio je vojnu skolu. U dvadesetosmoj godini zbog ucesca u revolucionarnoj organizaciji bio je osuden na smrt. Nakon pomilovanja proveo je cetiri godine na prisilnom radu u Sibiru.\n\nOn je jedan od najuticajnijih pisaca ruske knjizevnosti. Prema sirini i znacaju uticaja, posebno u modernizmu, on je bio svetski pisac u rangu Sekspira i Servantesa. Realizam Dostojevskog predstavlja svojevrsni prelaz prema modernizmu, jer njegovo stvaranje upravo u epohi modernizma postaje nekom vrstom uzora nacina pisanja. Sa aspekta knjizevne tehnike njegovi su romani jos uvek bliski realizmu zbog obuhvata celine, nacina karakterizacije i dominirajuce naracije, dok dramaticni dijalozi, filozofske rasprave i polifonija cine od njega pretecu modernizma. Utemeljitelj je psiholoskog romana...','src/slike_a/2.jpg'),(3,'Luke Welling','Luke Welling i Laura Thomson koriste PHP i MySQL i pisu o njima više od 15 godina. Cesto drze predavanja sirom sveta na vaznim konferencijama posvecenim softveru otvorenog koda. Luke je softverski inzenjer, a Laura direktor inzenjerskog razvoja u korporaciji Mozilla.','src/slike_a/3.png'),(4,'Mark Tven','Mark Tven je roden 30. novembra 1835. godine u gradicu Florida, drzava Missouri, samo oko 300 km udaljenom od indijanske teritorije.\n\nKada je imao 4 godine preselio se s roditeljima u Hannibal, Missouri, lucki gradic na Mississippiju sa oko 2000 stanovnika. Sa redovnim skolovanjem je zavrsio kada je imao 12 godina - 1847, kada mu je otac umro. Tad je postao pomocnik i ucenik dvojice vlasnika stamparije u Hannibalu. Kasnije je promijenio nekoliko poslova, uglavnom u vezi sa stampom. Do pocetka americkog gradanskog rata (1861-1865) nekoliko godina je radio kao kormilar parnog broda na Mississippiju. 1861. je nakratko bio dobrovoljac u konfederacijskoj konjici.\n\n1862. postao je reporter i humorist u listu Territorial Enterprise u Virginia Cityju, Nevada. 1863. poceo je potpisivati svoje clanke i ostale tekstove pseudonimom Mark Twain (izraz mornara na Mississippiju koji je oznacavao da je voda dovoljno duboka da brod moze proci)...','src/slike_a/4.jpg'),(5,'Laura Thomson','Luke Welling i Laura Thomson koriste PHP i MySQL i pisu o njima više od 15 godina. Cesto drze predavanja sirom sveta na vaznim konferencijama posvecenim softveru otvorenog koda. Luke je softverski inzenjer, a Laura direktor inzenjerskog razvoja u korporaciji Mozilla.','src/slike_a/5.jpg'),(6,'Herbert Schildt',NULL,'src/slike_a/nema_slike_a.png'),(7,'Cay S. Horstmann','I grew up in Northern Germany and attended the Christian-Albrechts-Universität in Kiel, a harbor town at the Baltic sea. I received a M.S. in computer science from Syracuse University, and a Ph.D. in mathematics from the University of Michigan in Ann Arbor. For four years, I was VP and CTO of an Internet startup that went from 3 people in a tiny office to a public company. I now teach computer science at San Jose State University and held visiting appointments at universities in Germany, Switzerland, Vietnam, and Macau. In my copious spare time I write books and articles on programming languages and computer science education.','src/slike_a/7.jpg'),(14,'Rhonda Layfield',NULL,'src/slike_a/nema_slike_a.png'),(15,'John Paul Mueller',NULL,'src/slike_a/nema_slike_a.png'),(17,'Gary Cornell',NULL,'src/slike_a/nema_slike_a.png'),(18,'Jesse Libarty','Jesse Liberty je predsednik Liberty Associates Inc., gde obezbedjuje obuku za objektno-orijentisano programiranje, kao i podrsku, konsalting i programiranje po ugovoru. On je bio projektant softvera za Xerox, softver inzenjer za AT&T i potpredsednik odeljenja za razvoj City banke. Osim toga, autor je sest knjiga o programiranju i objektno-orijentisanoj analizi i dizajniranju.','src/slike_a/18.png'),(19,'George Omura','George Omura, arhitekta i ilustrator, koristi AutoCAD preko petnaest godina. Radio je na raznovrsnim projektima, od hotela u turistickim mestima, preko saobracajnica u metropolama do projekta poznate biblioteke u San Francisku. Diplomirao je na kalifornijskom univerzitetu Berkeley. Autor je do sada najprodavanijih knjiga o AutoCAD-u. Ostale Omurine knjige u izdanju Sybexa su Mastering AutoCAD 2002, Mastering AutoCAD 3D, AutoCAD 2000 Instant Reference i Mastering AutoCAD for Mechanical Engineers.','src/slike_a/19.jpg'),(20,'Brian Benton','Brian C. Benton je visi inzenjerski tehnicar, konsultant za CAD, autor, instruktor i popularan bloger sa dvadesetogodisnjim iskustvom u vise inzenjerskih oblasti. Autor je kolumne Tip Patroller u casopisu Cadalyst, izvrsni menadzer mesecnog biltena AUGI HotNews i prezenter na godisnjim konferencijama koje organizuje Autodesk University.','src/slike_a/20.jpg'),(21,'Maggie Norris','Megi Noris (Maggie Norris) - honorarni publicista i menadzer projekata (urednik) za korporativne klijente u oblasti farmacije, biotehnologije i industrije medicinskih aparata.','src/slike_a/nema_slike_a.png'),(23,'Donna Rae Siegfried','Dona Re Zigfrid (Donna Rae Siegfried) - koautor knjige Biology For Dummies; objavljivala je autorske tekstove u casopisima Prevention i Men’s Health.','src/slike_a/nema_slike_a.png'),(24,'Zoran Modli','Poput mnogih drugih, tako je i Zoran Modli ro?en sredinom prošlog veka u Zemunu i za sada je z?iv i zdrav. Nije odmah postao pilot. Najpre je kao odlikas? zavrs?io osnovnu s?kolu, a onda alarmantno srozao uspeh u Prvoj zemunskoj gimnaziji. Od mature se oporavio u redakciji „Politike ekspres”, a sa dvadesetak godina proslavio kao revolucionarni disk-dz?okej Studija B i legendarne zemunske diskoteke „Sinagoga”. Studio B je, posle pet godina, napustio iz vis?e razloga, a najvis?e zbog letenja. Od tada je jednom nogom u raznim radijima, a drugom i obema rukama u avijaciji.','src/slike_a/24.jpg'),(25,'Bruce Schneier','Bruce Schneier is an internationally renowned security technologist, called a \"security guru\" by The Economist. He is the author of 13 books-- including Data and Goliath: The Hidden Battles to Collect Your Data and Control Your World--as well as hundreds of articles, essays, and \nacademic papers. His influential ewsletter \"Crypto-Gram\" and his blog \"Schneier on Security\" are read by over 250,000 people. He has testified before Congress, is a frequent guest on television and radio, has served on several government committees, and is regularly quoted in the press. Schneier is a fellow at the Berkman Klein Center for Internet & Society at Harvard University; a Lecturer in Public Policy at the Harvard Kennedy School; a board member of the Electronic Frontier Foundation, AccessNow, and the Tor Project; an Advisory Board Member of the Electronic Privacy Information Center and VerifiedVoting.org; and a special advisor to IBM Security and the Chief Technology Officer at IBM Resilient.','src/slike_a/25.jpg'),(27,'David Shier','My interest in physiology research and\nteaching began with a job as a research\nassistant at Harvard Medical School from\n1976-1979. I completed my Ph.D. at the\nUniversity of Michigan in 1984 and served\non the faculty of the Medical College\nof Ohio from 1985-1989. I have been\nteaching Anatomy and Physiology and\nPathophysiology full-time at Washtenaw\nCommunity College since 1990 and contributing as a member of the author team for the Hole texts since 1993.','src/slike_a/27.png'),(28,'Jackie Butler','My science career began in research at M.D. Anderson Hospital, where teaching was not one of my responsibilities. My masters committee at Texas A & M University quickly realized where my heart was. After I taught labs at Texas A & M for three years, they strongly recommended that I seek a teaching position when I relocated after graduation. As a result of their encouragement, I began teaching at Grayson County College in 1981. Many years later, I still feel excited and enthusiastic about being in the classroom.','src/slike_a/28.png'),(29,'Ricki Lewis','My career as a science communicator began with earning a PhD in genetics from Indiana University in 1980, and quickly blossomed into writing for newspapers and magazines, writing the introductory textbook Life, and teaching at several universities. Since then I have published many articles, the textbook Human Genetics: Concepts and Applications, an essay collection, and most recently my first novel. I love the challenge of being part of the Hole team.','src/slike_a/29.png'),(30,'Petar II Petrovic Njegos','Petar II Petrovic Njegos bio je crnogorski vladar, vladika, pjesnik i filozof. Rodjen je 13. novembra 1813. godine u selu Njegusi blizu Cetinja, a umro je 31. oktobra 1851. godine u Cetinju. Njegova se djela svrstavaju u red najznacajnijih dijela crnogorske knjizevnosti.','src/slike_a/30.jpg'),(31,'Dan Holme','With a career spanning more than 20 years, Dan Holme, a native of Colorado and graduate of Yale, earned a reputation as one of the world\'s most respected consultants, authors, thought leaders, and experts on Microsoft technologies. In April of 2016, Dan joined Microsoft as the Director of Product Marketing. New to Seattle after ten years on Maui, Hawaii, Dan is passionate about nature, the performing arts, and the Olympic Games. Dan has served as the Microsoft Technologies Consultant for NBC Sports for the broadcast of the Olympics in Rio, Sochi, London, Vancouver, Beijing and Torino.','src/slike_a/31.jpg'),(32,'Nelson Ruest','Nelson Ruest is an enterprise architect specializing in change management. During his twenty-year career, he has served as a computer operator, network administrator, and director for IT consulting firms. He is a Microsoft Certified Systems Engineer and Microsoft Certified Trainer. He is head of the Microsoft Windows 2000 Deployment Track for several Comdex Conference Programs, and is a frequent guest speaker at Comdex and other conferences in North America.','src/slike_a/32.jpg'),(33,'Danielle Ruest','Danielle Ruest is a workflow architect and consultant focused on people and organizational issues for large IT deployment projects. During her twenty-year career, she has led change management processes, developed and delivered training, and managed communications programs during process-implementation projects.','src/slike_a/33.jpg'),(34,'John Evans','John Evans has over 25 years experience in digital graphics, initially as a graphic designer and digital illustrator, and then as a multimedia author, user interface designer, and technical writer. He is the author of several editions of Peachpit\'s Adobe Photoshop Elements Classroom in a Book and Adobe Photoshop Lightroom Classroom in a Book, and he resides in Thailand.','src/slike_a/34.jpg'),(35,'Katrin Straub','Katrin Straub is an artist, designer, and author who holds degrees from the FH Augsburg, ISIA Urbino, and The New School University in New York. She has authored many books, among them Adobe Creative Suite Idea Kit, Adobe Soundbooth CS3 Classroom in a Book and several versions of Adobe Photoshop Elements Classroom in a Book, Adobe Premiere Elements Classroom in a Book, and Adobe Photoshop Lightroom Classroom in a Book. She resides in Brussels, Belgium.','src/slike_a/35.jpg'),(36,'James F. Kurose','Jim Kurose (born 1956) is a Distinguished University Professor in the College of Information and Computer Sciences at the University of Massachusetts Amherst. He was born in Greenwich, Connecticut, USA. He received his B.A. degree from Wesleyan University (physics) and, in 1984, his Ph.D. degree from Columbia University (computer science). Kurose\'s main area of teaching is computer networking. He is a coauthor of the well-known textbook Computer Networking: A Top-Down Approach.','src/slike_a/36.jpg'),(37,'Keith Ross','Keith Ross is the Dean of Engineering and Computer Science at NYU Shanghai and the Leonard J. Shustek Chair Professor of Computer Science at NYU. He also holds an affiliated appointment with the Department of Computer Science at the Courant Institute of Mathematical Sciences and with the Center for Data Science at NYU.','src/slike_a/37.jpg'),(38,'Paul Atkinson','Paul Atkinson works for Huron Healthcare as a BI Architect and Team Lead developing both traditional and real-time BI solutions. His training classes in high-performance TSQL programming are among the most popular course offerings available at Huron.','src/slike_a/nema_slike_a.png'),(39,'Robert Vieira','Robert Vieira is a Software Architect with Huron Consulting Group and is considered one of the leading authorities on Microsoft SQL Server. He speaks at conferences nationally and is well known for his unique tutorial approach in his teaching and writing.','src/slike_a/39.jpg'),(40,'aaa','wwwwwwwwwwwwwwwwww\nwwwwwwwwwww','src/slike_a/40.jpg');
/*!40000 ALTER TABLE `AUTOR` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `IZDAVAC`
--

DROP TABLE IF EXISTS `IZDAVAC`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `IZDAVAC` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `Naziv` varchar(40) NOT NULL,
  PRIMARY KEY (`Id`),
  UNIQUE KEY `Naziv_UNIQUE` (`Naziv`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `IZDAVAC`
--

LOCK TABLES `IZDAVAC` WRITE;
/*!40000 ALTER TABLE `IZDAVAC` DISABLE KEYS */;
INSERT INTO `IZDAVAC` VALUES (9,'Adobe Press'),(12,'Bard Fin - Romanov knjiga'),(2,'CET'),(3,'Kompjuter biblioteka'),(7,'McGraw-Hill'),(1,'Mikro knjiga'),(5,'Mladinska knjiga'),(10,'Nepoznat'),(8,'Nova knjiga'),(4,'Prentice Hall'),(11,'Tobogan'),(6,'W. W. Norton & Company');
/*!40000 ALTER TABLE `IZDAVAC` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `KNJIGA`
--

DROP TABLE IF EXISTS `KNJIGA`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `KNJIGA` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `Naslov` varchar(100) NOT NULL,
  `Oblast_Id` int(11) DEFAULT NULL,
  `Godina` int(11) DEFAULT NULL,
  `Opis` varchar(1000) DEFAULT NULL,
  `Br_strana` int(11) DEFAULT NULL,
  `Izdavac_Id` int(11) DEFAULT NULL,
  `Slika` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`Id`),
  UNIQUE KEY `Naslov_UNIQUE` (`Naslov`),
  KEY `Oblast_Id` (`Oblast_Id`),
  KEY `Izdavac_Id` (`Izdavac_Id`),
  CONSTRAINT `KNJIGA_ibfk_1` FOREIGN KEY (`Oblast_Id`) REFERENCES `OBLAST` (`Id`),
  CONSTRAINT `KNJIGA_ibfk_2` FOREIGN KEY (`Izdavac_Id`) REFERENCES `IZDAVAC` (`Id`)
) ENGINE=InnoDB AUTO_INCREMENT=52 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `KNJIGA`
--

LOCK TABLES `KNJIGA` WRITE;
/*!40000 ALTER TABLE `KNJIGA` DISABLE KEYS */;
INSERT INTO `KNJIGA` VALUES (1,'Windows Server 2003 - Do kraja',2,2003,'Najsveobuhvatnija i svakako najrazumljivija knjiga na ovu temu, Windows Server 2003 - Do kraja, za njenog visestruko nagradjivanog autora, Mark Minasija, predstavlja samo nastavak tradicije kristalno jasne i sveobuhvatne obrade Microsoftovih proizvoda iz familije Windows Server. Ova knjiga ce Vam pokazati kako da isplanirate, konfigurisete i instalirate sopstvenu kompjutersku mrezu, kako da njeno funkcionisanje dovedete do savrsenstva i na koji nacin da je popravite ukoliko dodje do njenog sloma. Ali, to nije sve — knjiga i dalje sadrzi sve sto je potrebno da znate u vezi instalacije Windows 2000 Servera, koji i dalje ostaje vazan sastavni deo Vaseg radnog okruzenja.',1800,3,'src/slike/1.jpg'),(3,'C++ Naucite za 21 dan (VII)',2,2003,'Naucite C++ za 21 dan, predstavlja prirucnik-vodic za ucenje objektno-orijentisanog dizajniranja, programiranja i analize. Pomocu ove knjige steci cete celovito znanje o svim baznim konceptima, ukljucujuci programski tok, upravljanje memorijom, kompajliranje i debagovanje. Da biste lakse pamtili i ucili, knjigu smo osmislili u formi radionice, podelivsi je na 21 poglavlje, tj. na 21 dan. Koncepti su razlozeni na poglavlja koja se lako savladavaju pomocu brojnih listinga koji ilustruju ne samo kod, vec ukazuju kako da taj kod ucinite jos boljim. Ovo izdanje je kompletno revidirano, azurirano i u skladu sa ANSI standardom.',786,3,'src/slike/3.jpg'),(5,'Anatomija i fiziologija za neupucene (II)',5,2014,'Ovaj detaljan vodic pomoci ce vam da shvatite znacenje strucnih termina koji se koriste u anatomiji i fiziologiji, upoznate anatomske strukture tela i steknete uvid u to kako te strukture i sistemi funkcionisu. Napisan jednostavnim jezikom i opremljen s nekoliko desetina sjajnih ilustracija, donosi vam fascinantna saznanja o ljudskom telu.',344,1,'src/slike/5.gif'),(6,'Proba 5',12,5000,NULL,NULL,10,'src/slike/nema_slike.png'),(12,'Proba 1',12,5000,NULL,NULL,10,'src/slike/nema_slike.png'),(18,'proba 3',12,5000,NULL,NULL,10,'src/slike/nema_slike.png'),(19,'AutoCAD 2004 i AutoCAD LT 2004',2,2004,'AutoCAD 2004 i AutoCAD LT 2004 je azurirano i poboljsano izdanje Omurinog klasika. Ako nikada niste koristili AutoCAD, postupna uputstva i vezbe pomoci ce vam da odmah pocnete. Ukoliko imate iskustva s ranijom verzijom programa, brzo cete preci na visi nivo zahvaljujuci detaljnim objasnjenjima najnovijih i najnaprednijih funkcija AutoCAD-a. Bez obzira na prethodno iskustvo i nacin na koji koristite AutoCAD, ova knjiga ce biti nezamenljiv izvor podataka kojem cete se stalno vracati.',1112,1,'src/slike/19.gif'),(22,'PHP i MySQL: razvoj aplikacija za veb (V)',2,2017,'Knjiga PHP i MySQL: razvoj aplikacija za veb opisuje kako se kombinacija te dve alatke moze upotrebiti za izradu efikasnih interaktivnih veb aplikacija. Prvo su detaljno objasnjene osnove jezika PHP i kako se pravi i koristi MySQL baza podataka, a zatim se prelazi na upotrebu PHP-a za rad sa bazom podataka i veb serverom.',746,1,'src/slike/22.gif'),(24,'proba 2',12,5000,NULL,NULL,10,'src/slike/nema_slike.png'),(25,'Java (SE 7) Tom I - Osnove (IX)',2,2013,'Ova knjiga je namenjena ozbiljnim programerima i predstavlja pouzdan, objektivan i konkretan vodic koji kroz detaljno testirane primere koda istice kljucne osobine Java programskog jezika i biblioteke gotovih klasa. Kao i u prethodnim izdanjima, kod je lako razumljiv i odrazava trenutno najbolje tehnike u programiranju. Takodje, kod je tako napisan da vam pomaze da ubrzate razvoj vasih projekata.',1004,2,'src/slike/25.jpg'),(26,'Windows Server 2008 - umrežavanje',2,2009,'U ovoj knjizi objasnjene su osnove tehnologija koje osoblje zaduzeno za racunarske mreze mora da poznaje. Naucite kako se instalira Windows Server 2008 i kako se pravi jednostavna racunarska mreza, naucite osnovne koncepte uspostavljanja zastite i upravljanja Windows Serverom.',500,3,'src/slike/26.jpg'),(27,'AutoCAD 2017 i AutoCAD LT 2017',2,2017,'Knjiga AutoCAD 2017 i AutoCAD LT 2017 najprodavaniji je prirucnik za AutoCAD svih vremena. Uz jasna objasnjenja, sazete primere, postupna uputstva i mnogo prakticnih vezbi, autorski tim eksperata vodi vas kroz sve sto treba da znate kako biste efikasno koristili AutoCAD. Ovo azurirano i dopunjeno izdanje obradjuje nove mogucnosti poslednje verzije AutoCAD-a.\n\nBez obzira na to da li ste pocetnik ili se pripremate za sertifikaciju, ovaj prirucnik ce vam biti nezamenljiv izvor znanja. Saveti, trikovi i uputstva za rad pokrivaju svaki aspekt AutoCAD-a – ukljucujuci i ispit za sticanje sertifikata – tako da vam je obezbedjena i obuka od pocetka do kraja i dragocen referentni materijal.',1020,1,'src/slike/27.gif'),(29,'proba 4',12,5000,NULL,NULL,10,'src/slike/nema_slike.png'),(30,'Pilotska knjiga',9,2012,'Nova knjiga dugogodišnjeg saobra?ajnog pilota i instruktora letenja Zorana Modlija doživela je i drugo izdanje! Knjiga, ina?e, objedinjuje sve ono što je dosadašnje generacije pilota kao magnet privla?ilo leta?kom pozivu kroz re?i svojevremeno ispisane na stranicama njegove \"Krilate katedre, \"Škole letenja\", \"Pista u no?i\"... Najnovija, \"Pilotska knjiga\", treba da bude atraktivan, zanimljiv i do sada najkompletniji vodi? kroz osnovnu školu pilotaže za 21. vek, na 528 strana, sa poklon-DVD-om na kojem je film \"Škola letenja\". Možete je potražiti u knjižarskim lancima \"Vulkan\", \"Delfi\", \"Mladinska knjiga\" i još nekim knjižarama koje ne zaziru od (osrednje) skupih knjiga. I pilota!',528,5,'src/slike/30.jpg'),(31,'Data and Goliath',2,2015,'Your cell phone provider tracks your location and knows who’s with you. Your online and in-store purchasing patterns are recorded, and reveal if you\'re unemployed, sick, or pregnant. Your e-mails and texts expose your intimate and casual friends. Google knows what you’re thinking because it saves your private searches. Facebook can determine your gender orientation without you ever mentioning it.\n\nThe powers that surveil us do more than simply store this information. Corporations use surveillance to manipulate not only the news articles and advertisements we each see, but also the prices we’re offered. Governments use surveillance to discriminate, censor, chill free speech, and \nput people in danger worldwide. And both sides share this information with each other or, even worse, lose it to cybercriminals in huge data breaches.',295,6,'src/slike/31.png'),(39,'Human Anatomy & Physiology (XII)',5,2010,'The level of this text is geared\ntoward students in two-semester\ncourses in anatomy and physiology\nwho are pursuing careers in nursing\nand allied health fields and who have\nminimal background in physical and\nbiological sciences. The first four chapters review chemistry and physiological\nprocesses. Students who have studied\nthis material previously will view it as\na welcomed review, but newcomers\nwill not find it intimidating.',1012,7,'src/slike/39.png'),(42,'PHP i MySQL: razvoj aplikacija za Web (II)',2,2004,'Knjiga PHP i MySQL: razvoj aplikacija za Web opisuje kako se kombinacija te dve alatke moze upotrebiti za izradu efikasnih interaktivnih Web aplikacija. Prvo su detaljno objasnjene osnove jezika PHP i kako se pravi i koristi MySQL-ova baza podataka, a zatim se prelazi na upotrebu PHP-a za rad s bazom podataka i serverom.',816,1,'src/slike/42.jpg'),(43,'Prirucnik za MySQL',2,2005,'Ova knjiga je jedinstvena jer se sastoji od niza kratkih i sazetih poglavlja, a svako je usredsredjeno na odredjenu temu i prevashodno opisuje kako se obavljaju odredjeni poslovi. Svako poglavlje zavrsava se pitanjima i vezbama koje se odnose na gradivo poglavlja, sto vam omogucava da ispitate da li ste dobro razumeli izlozene koncepte.',264,1,'src/slike/43.jpg'),(44,'Gorski vijenac - Luca mikrokozma',4,2014,'Gorski vijenac je narodno-herojski ep koji u svojoj osnovi ima istorijski dogadjaj. Djelo izuzetne vrijednosti u kojem je Njegos, kroz jedan istorijski dogadjaj, opjevao zivot Crnogoraca - njihovu istoriju i tradiciju, njihove obicaje, vjerovanja i shvatanja. Njegos je u Gorskom vijencu opjevao najvaznije dogadjaje iz proslosti, od vremena Nemanjica do pocetka XVIII vijeka, naslikao svakodnevni crnogorski zivot i prikazao susjedne narode, Turke i Mlecane.\n\nLuca mikrokozma je filozofsko-religiozni spjev u kojem je pjesnik tragao za odgovorom na pitanje o porijeklu zla u svijetu i nasao ga u vanzemaljskim, metafizickim uzrocima. Luca nije samo zbir njegovih emocionalnih i zivotnih drama i lomova kao covjeka, duhovnika i drzavnika. Ona je izraz njegovog bica i njegovih misaonih preokupacija. Luca mikrokozma predstavlja borbu dobra i zla, Boga i djavola, u kojoj je Bog pobjednik, jer je on stvaralac svega pa i samog djavola.',225,8,'src/slike/44.jpg'),(46,'Adobe Photoshop - Classroom in a Book',2,2015,'Serious digital photographers, amateur or pro, who seek the fastest, easiest, most comprehensive way to learn Adobe Photoshop Lightroom CC / Lightroom 6 choose Adobe Photoshop Lightroom CC 2015 release / 6 Classroom in a Book from the Adobe Creative Team at Adobe Press. The 11 project-based lessons in this book show readers step-by-step the key techniques for working in Photoshop Lightroom CC / 6. And a stunning showcase of extraordinary images by working professional photographers provides the perfect inspiration for your next project.',408,9,'src/slike/46.jpg'),(47,'Umrezavanje racunara - Od vrha ka dnu (VI)',2,2014,'Umrezavanje racunara: Od vrha ka dnu je udzbenik koji se najvise upotrebljava za ucenje osnova umrezavanja. Istaknuti profesori James Kurose i Keith Ross drze paznju studenata svojim privlacnim stilom pisanja i svojim pristupom od vrha ka dnu u objasnjavanju umrezavanja i interneta. Oslanjajuci se na uspeh prethodnih izdanja, ovo sesto izdanje nastavlja sa naglaskom na paradigme aplikacionog sloja i na aplikaciono programiranje ohrabrujuci da se sto pre \"umoce ruke\" u protokole i koncepte umrezavanja. U ovom izdanju Kurose i Ross u prvi plan stavljaju pitanja mrezne bezbednosti, pored ukljucivanja najnovijih i relevantnih tehnologija umrezavanja.',892,2,'src/slike/47.gif'),(48,'Microsoft SQL Server 2012 programiranje',2,2013,'SQL Server 2012 je najnovije otelovljenje sistema za upravljanje bazama podataka koji se koristi vise od dve decenije. Ova knjiga se fokusira na kljucne potrebe svakog programera, bez obzira na kom su nivou njegove vestine. U sredistu paznje se u velikoj meri nalazi samo verzija 2012, ali se takode redovno pominju pitanja kompatibilnosti sa prethodnim verzijama, posto mogu uticati na vas izbor za dizajn i kodiranje.',866,2,'src/slike/48.jpg'),(49,'Dozivljaji Toma Sojera',13,2005,'Tom Sojer je nestašni djecak koji nema roditelje, pa zivi sa svojom tetka Poli, malim polubratom Sidom i sestrom Meri. Ima loknastu, smedu kosu i plave oci. Ne voli da ide u skolu zbog toga sto treba rano da ustaje, mora da se ceslja i lijepo obuce, jer je obicno bio rascupan i obucen u dronjke. Najuzbudljiviji dijelovi romana su kada Tom i Haklberi bjeze na ostrvo, kada traze blago i kada se Tom i Rebeka (Bekica) izgube u pecini. Poznat je po svojim pustolovinama i nestaslucima na obali rijeke Misisipi u SAD-u.',283,11,'src/slike/49.jpg'),(50,'Idiot',13,2017,'U Idiotu imamo tipicnog pozitivnog dostojevskijanskog junaka. To je knez Miskin, obdaren dobrotom i sposobnoscu da prasta. Miskin je zacudujuce osetljiv, do krajnjih granica: on oseca sve sto se zbiva u drugim ljudima, cak i kad su ti ljudi kilometrima daleko. Veoma je darezljiv i naivan da bi se mogao nazvati idiotom, odakle i naziv za sam roman. Tako je duboka njegova duhovna mudrost, njegovo saosecanje i razumevanje patnji drugih. Knez Miskin je sama cistota, iskrenost, otvorenost; ove osobine ga neizbezno uvode u bolne sukobe sa nasim konvencionalnim, vestackim svetom. Njega vole svi koji ga poznaju...',771,12,'src/slike/50.png'),(51,'windows 2008',2,5000,NULL,NULL,1,'src/slike/51.jpg');
/*!40000 ALTER TABLE `KNJIGA` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `NAPISAO`
--

DROP TABLE IF EXISTS `NAPISAO`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `NAPISAO` (
  `Autor_Id` int(11) NOT NULL,
  `Knjiga_Id` int(11) NOT NULL,
  PRIMARY KEY (`Autor_Id`,`Knjiga_Id`),
  KEY `Knjiga_Id` (`Knjiga_Id`),
  CONSTRAINT `NAPISAO_ibfk_1` FOREIGN KEY (`Autor_Id`) REFERENCES `AUTOR` (`Id`),
  CONSTRAINT `NAPISAO_ibfk_2` FOREIGN KEY (`Knjiga_Id`) REFERENCES `KNJIGA` (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `NAPISAO`
--

LOCK TABLES `NAPISAO` WRITE;
/*!40000 ALTER TABLE `NAPISAO` DISABLE KEYS */;
INSERT INTO `NAPISAO` VALUES (1,1),(18,3),(21,5),(23,5),(19,19),(3,22),(5,22),(7,25),(17,25),(1,26),(14,26),(15,26),(19,27),(20,27),(24,30),(25,31),(27,39),(28,39),(29,39),(3,42),(5,42),(3,43),(5,43),(30,44),(34,46),(35,46),(36,47),(37,47),(38,48),(39,48),(4,49),(2,50),(31,51),(40,51);
/*!40000 ALTER TABLE `NAPISAO` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `OBLAST`
--

DROP TABLE IF EXISTS `OBLAST`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `OBLAST` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `Naziv` varchar(20) NOT NULL,
  PRIMARY KEY (`Id`),
  UNIQUE KEY `Naziv_UNIQUE` (`Naziv`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `OBLAST`
--

LOCK TABLES `OBLAST` WRITE;
/*!40000 ALTER TABLE `OBLAST` DISABLE KEYS */;
INSERT INTO `OBLAST` VALUES (11,'aaa'),(4,'Filosofija'),(2,'IT'),(13,'Knjizevnost'),(5,'Medicina'),(12,'Neodredjena'),(9,'Vazduhoplovstvo');
/*!40000 ALTER TABLE `OBLAST` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2019-03-18 13:34:33

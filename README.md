# Uvod

Aplikacija koristi API servis za dobijanje informacija o filmovima, serijama i njihovim epizodama.
Od podataka koje API poziv vraća, oni koji se kroz aplikaciju najčešće koriste su:

* Naziv filma ili serije,
* Opis radnje medija,
* imdbID koji na jedinstven način obilježava određeni medij,
* Link na kojem se nalazi poster filma
* imdbRating koji predstavlja recenziju Korisnik ima mogućnost unosa string-a po kojem će se vršiti
  pretraživanje, kao i biranje podataka koji će predstavljati kriterij za filtriranje podataka.
  Rezultat predrage se prikazuju kroz RecyclerView, a kada korisnik klikne na jedan od Item-a,
  prikazuju se detaljne informacije o filmu ili seriji. Korisnik također ima opciju spašavanja
  medija u svoj "Watchlist" i opciju preporučivanja filma prijatelju u vidu tekstualne poruke.
  Dalje, u posebnoj komponenti se čuva i galerija postera svih medija spašenih u Watchlist.

# API

Korišten je [OMDb API] (https://www.omdbapi.com/), koji predstavlja RESTful web servis za dohvatanje
informacija o medijskom sadržaju. Sam API koristi IMDB servise za dohvatanje informacija, a u našu
aplikaciju ih proslijeđuje u JSON formatu. API pozivi na server su izvedeni koristeći GET metodu, u
kojoj je kao obavezan parametar proslijeđen API ključ, kao i imdbID medije čije detaljne informacije
želimo da dobijemo ili search parametar koji nam vraća listu filmova čiji se naziv podudara sa
istim. Od neobaveznih parametara, tu su:

* type - željeni tip koji može imati vrijednosti "movie", "episode" ili "series"
* y - godina izlaska filma ili serije
* plot - možemo proslijediti dvije vrijednosti - "short" ili "full", a one određuju da li ćemo u
  response-u dobiti detaljan opis radnje ili ne
* page - koristi se prilikom pagiacije, koji mi ovdje nismo implementirali

# Dizajn aplikacije

Sam izgled aplikacije je minimalistički. Korišteno je svega par boja, i to određena nijansa plave
koja služi kao primarna boja kroz aplikaciju. To znači da su svi naglasci unutar aplikacije obojeni
ovom bojom. Boja koja dominira User Interface-om jeste bijela koja je prisutna kao pozadina svakog
fragmenta, te pozadina skoro svakog view-a. Kao distinkciju pozadine i view-ova koristili smo sjene.
Tekst kroz aplikaciju obojen je ili bijelom ili nijansom sive (#333) zavisno od boje podloge.
Kontrast je uvijek na nivou čime je osiguran ugodan User Experience.

## Stil view-ova

Kako bi osigurali konstantnost i konzistentost kroz dizajn aplikacije, svi view-ovi imaju isti ili
približno isti stil, a to podrazumijeva sljedeće:

* sjene iza view-a za bolju vidljivost i kontrast u odnosu na pozadinu,
* zaobljenje uglove,
* istu pozadinu. Ovo je postignuto tako što je napravljen zaseban drawable file pod nazivom "
  rounded_corner". Isprva, ovaj file je trebao sadržavati isključivo kod za zaobljivanje uglova
  view-a iz razloga što isto ne možemo postići kroz predefinisane Android atribute. Međutim, kako bi
  zaobišli dodatan boilerplate kod za sve view-ove, stilizovanje sjena i pozadine odrađeni su u
  istom file-u.  
  Pozadina i zaobljeni uglovi definisani su pomoću atributa "color" i "radius" koji su dostupni u "
  item" i "corners" elementima unutar drawable file-a.  
  Sjene su urađene tako što je ispod view-a poredano sedam item-a koji su redom veći za 1dp od
  prethodnog, te svaki sljedeći ima malo blažu boju pozadine. Tako je postignut efekat ravnomjerno
  raspoređene sjene.  
  Ovaj drawable file dalje jednostavno koristimo unutar "background" atributa za bilo koji view, što
  na taj isti view primjenjuje gore objašnjene stilove.

# Dizajn fragmenata

Dizajn svih fragmenata vodi se gore navedenim smjernicama, međutim detaljnije ćemo objasniti svaki
fragment posebno.

## Uvodni fragment

Predstavlja prvi fragment na kojeg korisnik nailazi prilikom korištenja aplikacije. Glavni meni logo
aplikacije, u vidu slike režiserske tablice koja jasno daje do znanja o kakvoj se aplikaciji radi.
Ispod logo-a nalaze se tri dugmića i to:

* Browse Movies - vodi na fragment za pretragu filmova,
* My Watchlist - vodi na fragment sa spašenim filmovima,
* Poster Gallery - vodi na fragment sa svim posterima filmova koji su poredani pomoću GridView-a.
  Cijeli fragment nalazi se u Constraint layout-u. Pozadinu je predstavljena kao obični ImageView
  koji zauzima cijelu širinu i visinu ekrana, te sadrži atribut "scaleType" koji je postavljen na
  vrijednost "centerCrop". To omogućava da se slika uvijek prilagodi ekranu i da ne ostaje prazan
  prostor oko slike što bi narušilo User Experience.  
  Glavni meni kao i sličica poredani su unutar Linear layout-a. Ovaj layout vertikalno smo
  centrirali pomoću gornjeg i donjeg constrainta, te od lijeve ivice ekrana odvojili postavljanjem
  lijevog paddinga na 50dp. Između svakog elementa linearnog layouta stavili smo određenu marginu
  zbog preglednosti. Dugmići su iste širine i fonta.

### ConstraintLayout

ViewGroup element (View koji sadrži druge View-e) koji omogućava kreiranje kompleksnih i velikih
layout-a bez pretjeranog ugnježđivanja View-a, što znači da ima poprilično jednostavnu hierarhiju.
Ovo mu ujedno predstavlja i najveću prednost, jer znači da je učitavanje View-a dosta brže u odnosu
na ostale ViewGroup-e. Položaji View-a u njemu su definisani pomoću constraint-a i ovom pogledu je
dosta sličan RelativeLayout-u. Pored ovoga, ConstraintLayout je dosta fleksibilan i dobro
funkcioniše Layout Editorom unutar Android Studija

## Fragment za pretragu filmova

Prva opcija iz glavnog menija jeste Browse Movies, na čiji se klik otvori fragment za pretragu
filmova i serija. Tu se nalazi polje za input preko kojeg korisnik može pretraživati, te Radio
dugmići za odabir vrste pretrage. To uključuje pretragu samo filmova ili samo serija, pretragu
epizoda, te opciju koja je po defaultu označena, a to je opcija "Show all" koja kako i sam naziv
nalaže predstavlja pretragu po svim parametrima.  
Način na koji su elemnti raspoređeni sličan je kao i kod uvodnog fragmenta. Imamo top level
Constraint layout koji zauzima cijeli ekran i čiji padding iznosi 50dp. Unutar ovog layout-a nalazi
se Linear layout u kome su sadržani svi ostali elementi. Linear layout ima padding od 10dp zbog
preglednosti i vertikalnu orijentaciju, tako da se view-ovi slažu jedan na drugi. Na početku imamo
EditText sa "Search" placeholderom i "ems" atributom vrijednosti 10, što u suštini označava veličinu
fonta placeholdera i teksta koji se kuca u polje. Ispod polja za unos nalazi se RadioGroup view koji
služa kao wrapper za radio dugmiće. Boja dugmića je primarna boja koju smo koristili kroz aplikaciju
za naglaske, a atribut pomoću kojeg je postavljenja boja jeste "buttonTint". Defaultni označeni
dugmić je opcija "Show all".  
Ispod svega nalazi se Button "Apply" koji primjenjuje korisnikove odabire te otvara novi fragment sa
rezultatima pretrage.

## Fragmet MovieList

Filmovi su prikazivani pomoću RecyclerView-a, i to kao kartice. Kartica je implementirana kao
CardView visine 125dp, te maksimalne moguće širine. Kartica ima zakrivljene uglove vrijednosti 10dp
i "cardElevation" vrijednosti 7dp koji daje određenu sjenu.  
Unutar CardView-a nalazi se Constraint layout pomoću kojeg su svi elementi kartice pozicionirani.
Pozadina je implementirana na isti način kao i kod uvodnog fragmenta. To je poster filma ili serije
kao ImageView koji zauzima cijelu širinu i visinu sa centriranim skaliranjem. Međutim, kako su
posteri raznih boja, kontrast sa nazivom filma ili serije preko bio bi izrazito slab što bi
uzrokovalo poremećen User Experience. Ovaj problem je riješen tako da je poviš slike postera
stavljen overlay sa polutransparentom pozadinom. Na ovaj način svi posteri će izgledati polubijelo
što nam osigurava dovoljno dobar kontrast sa tekstom.  
Naslov filma ili serije je 30sp velik tekst koji je vertikalno centriran na sredinu, a horizontalno
prema lijevo.Također, ikona centrirana u desno jasno korisniku daje do znanja da može kliknuti
karticu.

### RecyclerView

Više fragmenata u aplikaciji implementira RecyclerView kao ViewGroup. To je Android Jatpack
biblioteka koja optimizuje prikazivanje velike količine podataka u listi. Naime, kako kreiranje
velike količine View-a i njihovo "napuhavanje" predstavlja zahtjevanu i skupocjenu operaciju za
Android, pokazuje se da je najbolje rješenje "reciklirati" postojeće View-e tako da samo promjenimo
njihov sadržaj, bez da radimo Inflate novih. RecyclerView radi upravo to, na način da kada neki Item
napusti vidokrug aplikacije, ponovo ga koristi za naredni Item. Ključna klasa potrebna za korištenje
RecyclerView-a jeste njegov adapter, u kojem kroz 3 funkcije definišemo na koji način će se ovo
recikliranje odvijati, šta će jedan List Item sadržati i kakve će funkcionalnosti imati.
RecyclerView pozitivno utiče na responzivnost aplikacije, performansu i smanjuje potrošnju baterije,
a samim time i poboljšava User Experience.

## Fragment MovieOverview

Na klik korisnika na jedan od filmova ili serija, otvara se fragment za pregled dodatnih informacija
datog filma ili serije. Ovo je najkompleksniji layout u aplikaciji. Cijeli fragment nalazi se unutar
ScrollView-a kako bi korisnik bio u mogučnosti skrolati. Dalje, postoji top level Constraint layout
u kome su sadržani svi ostali View-ovi.  
Na početku ovog layout-a imamo sliku postera koja je pozicionirana uz gornju ivicu ekrana, te
zauzima cijelu širinu ekrana. Visoka je 300dp. Slika pomoće "scaleType" atributa fino skalira prema
ekranu.  
Dalje imamo jedan child Constraint layout koji je također pozicioniran uz gornju ivicu ekrana, ali
je nešto manje visine od 250dp. Za ovaj layout napravljen je poseban drawable file "
gradient_overlay" u kome je pomoću "gradient" elementa postignut fini prelaz između transparentne i
crne boje. Na taj način tekst koji je na dnu layout-a ima dobar kontrast, a gradijent nestaje preko
slike. Ovako je osigurana vidljivost i postera i naslova koji se nalazi na njemu, a da pritom sve to
lijepo izgleda.  
Ispod naslova filma ili serije navedena je godina i vrijeme trajanja kao jedne od najvažnijih
informacija koje korisnik gleda.Ispod ovod layout-a nalazi se Linear layout koji se uvlači na
prostor slike te tako daje moderan i lijep izgled. To je postignuto tako što je gornja margina
layout-a staviljena na -35dp. Unutar ovog layout-a nalaze se zasebni Linear layout-i koji obuhvataju
kartice sa informacijama. Na kraju nalaze se dugmići, i to: dugmić za dodavanje filma u Watchlist,
ili u slučaju da je već u listi, prikazuje se dugmić za otklanjanje iz iste. Pored ovog, postoji i
dugmić za slanje preporuke filma kontatkima u vidu tekstualne poruke.

### Intent

Slanje poruke je postignuto koristeći Intent, koji predstavlja zahtjev Android operativnom sistemu
za korištenje neke druge aktivnosti, bilo iz naše aplikacije ili neke druge koja se nalazi na
uređaju. Tačnije, koristimo implicitni intent koji podrazumjeva da mi izrazimo namjeru za slanjem
poruke, a android operativni sistem nudi niz aplikacija koje mogu da izvrše slanje te poruke. U
suprotnom, da smo koristili explicitni intent, morali bismo navesti i aplikaciju i aktivnost koju
želimo da koristimo za slanje poruke (na primjer Gmail, Facebook Messenger i slično).

### Retrofit

Retrofit je Android biblioteka koja obrađuje, prihvata, kreira i šalje HTTP upite i odgovore na
jednostavan i korisniku jednostavan način. Retrofit zahtjeva Model klasu koja će upakovati rezultate
poziva, koji je u JSON formatu u Kotlin objekat. Retrofit ne vrši ovu pretvorbu sam, već je potrebno
koristiti dodatnu biblioteku, poput Moshi ili Gson; konkretno u ovom projektu je korištena Gson
biblioteka. U ovom slučaju, Retrofit koristi 2 model klase, i to MoviesResponse, koja sadrži niz
filmova i MovieDetails, koja vraća detaljne informacije o filmu ili seriji. Pored model klase,
potrebno je i da kreiramo instancu Retrofita, koje će ustvari i vršiti API pozive.

### Room baza podataka

Spašavanje filmova u Watchlistu se obavlja uz pomoć Room baze. To je biblioteka koja predstavlja
neki vid medijatora između programera i SQLite baze podataka. Sam developer ne pristupa bazi
direktno, već to radi posredstvom Room-a. Kako bi koristili Room, potrebno je implementirati njegove
3 glavne komponente, a to su:

* Entity koji je anotiran sa @Entity anotacijom i koji predstavlja Model klasu koja definiše kolone
  naše tabele. Atributi ove klase predstavljaju kolone u tabeli.
* Database - abstraktna klasa u kojoj definišemo sve tabele naše baze podataka
* DAO - Database access object, koji nam omogućava vršenje upita nad bazom

## Fragment PosterGrid

Kao što sam naziv nalaže, ovaj fragment sadrži sve postere filmova i serija iz Watchliste u jednoj
galeriji. Galerija je implementirana pomoću GridView-a, i to u dvije kolone u portrait orijentaciji
i četiri kolone kada je orijentacija landscape.  
Svaki element GridView-a implementiran je kao CardView u kome se nalazi jedan ImageView za poster
slike. U file-u "poster_grid" implementirana je galerija pomoću GridView elementa koji ima
horizontalni i vertikalni spacing od po 10dp i broj kolona 2 ili 4 u zavisno od vrste orijentacije,
portrait i landscape respektivno.  
Postoji i poseban adapter za GridView u kojem pomoću LayoutInflatera radimo Inflate kartice, te tako
dobijamo galeriju postera.

### Glide

Glide je brz i efikasan open source framework za ušitavanje slika u Android-u. Ovaj framework
optimizuje učitavanje slika, bilo unutar listi ili pojedinačnih View-a. Konkretno u ovoj aplikaciji
je korišten za ušitavanje svih slika.

## Ostali fragmenti

Ostali fragmenti i njihove funkcionalnosti su:

* Fragment za filtriranje medija, u kojem biramo parametre po kojima želimo vršiti pretragu
* Fragment za filtriranje filmova iz Watchlist-e putem izbornika
* Fragment za prikazivanje filmova iz Watchlist-e, pri čemu i ovdje imamo opciju prikazivanja
  detalja o filmu
* Info fragment u kojem se nalaze osnovne informacije o projektu i studentima koji su ga radili

### Spinner

Unutar fragmenta za filtriranje filmova u Watchlisti, implementirna su dva Dropdown menija pomoću
Spinner-a, koji su ugrađeni u Android. Potrebno je definisati SpinnerView uz specificiran id, te
širinu, visinu i pozadinu. U Kotlin klasi fragmenta se odvija glavna radnja. Svaki Spinner zasebno
smo povukli preko id-a te postavili njihove adaptere pomoću nizova za žanrove i godine. Žanrove se
čuvaju kao "string-array" unutar stringovnih resursa, te im u klasi pristupamo pomoću "
getStringArray" metode. Kao layout adaptera koristišten je fajl "spinner_list" u kome je
implementiran izgled jednog itema u listi.

## Navigacija

Aplikacija je organizirana kroz više fragmenta, a sami fragmenti su povezani pomoću navigacijone
komponente koja dolazi iz Android Jetpack-a. Ona nam omućava da na jednostavan i pregledan način
povežemo fragmente koji se nalaze u projektu i šaljemo podatke između njih. Glavna komponenta same
navigacije jeste navigation host, koji predstavlja prazan kontejner u kojem dolazi do mijenjanja
fragmenata dok korisnik prolazi kroz našu aplikaciju. U našem slučaju, ulogu navigation host-a uzima
MainActivity. Fragmente povezujemo na način da između njih definišemo akcije u XML fajlu navigacije.
Android zatim automatski generiše klase koje sadrže navedene akcije, koje možemo koristiti u Kotlin
fajlovima samih fragmenata. Kako bi slali podatke među destinacijama, moramo ih prethodno definisati
u XML-u, unutar argument tagova, i to fragmentu koji očekuje da primi te podatke. Prilikom
definisanja samih argumenata, neophodno je navesti njihov naziv i tip podataka, a imamo i mogućnost
unošenja podrazumjevane vrijednosti za neki podatak. Ovaj način prenosa podataka između destinacija
smo izvršili koristeći Safe Args Gradle plugin, koji osigurava “type safety” i koji se preporučuje
za korištenje od strane samog Android-a.

### DataBinding

Kroz projekat je korištena Data Binding biblioteka za pristupanje UI elementima aplikacije na
deklerativan način. Ovaj način je brži i sigurniji od starog; korištenje metode findViewById() je
znatno sporije i nesigurnije jer program na svakom mjestu njenog pozivanja mora proći kroz sve
View-e u potrazi za traženim ID-om. Koristeći Data Binding biblioteku, na početku fajla instanciramo
objekat koji je tipa Binding za tu klasu. U tom objektu se nalaze ID-evi svih View-a koji se nalaze
u tom layout-u. Na ovaj način onemogućujemo potragu za ID-em u slučaju da on ne postoji, što znači
da je Data Binding null safe.

## Caching

Poželjno je da korisnik prilikom korištenja aplikacije ima pristup internetu, međutim aplikacija
funkcionše i bez njega. Koriteći metodu koja provjerava da li uređaj ima pristup internetu,
aplikacija definiše svoje daljnje ponašanje. U slučaju da aplikacija nije povezana na internet,
korisnik ne može pretraživati filmove po nazivu, već mu se prikaže lista filmova iz prethodne
pretrage. Prilikom novog API poziva, aplikacija vrši spašavanje rezultata poziva u Bazu, i to u
trenutku kada dođe do uništenja fragmenta za pretragu. Također, vrši se i brisanje prethodnih
filmova spašenih u Cache memoriju. Spašavanje u Cache memoriju se vrši u trenutku uništavanja
fragmenta


        

# Legatura dintre clase si descrierea si justificarea design pattern-urilor folosite in cadrul aplicatiei.

- In aplicatie am utilizat 4 design patternuri pe care le-am considerat facile pentru a usura
dezvoltarea. Anume design patternul: Singleton, Builder, Command si Observer

- OBS: Pentru detalii mult mai explicite despre METODE, a se consulta comentariile javaDoc
din fiecare clasa!!!!
## Design Patternul Singleton:
- L-am utilizat in cadrul clasei Database, intrucat baza de date trebuie sa fie unica per sesiune, astfel
ca acest design pattern mi-a permis sa ii definesc un constructor privat si un camp static de tip
`private static Database databaseUnica` pe care sa il utilizez  in metoda `public static Database Instanta()`
care va fi folosita de catre user in clasa Main, dar si in comenzile specifice. Aceasta va verifica daca
atributul databaseUnica este null va construi baza de date cu ajutorul constructorului privat si returneaza
si in caz contrar ( a fost deja instantiata o data), doar o va returna.

## Design Paternul Builder:
- L-am folosit in cadrul a doua clase: Museum si Location, intrucat am observat ca in fisierul de tip CSV-like
nu mereu apareau toti parametrii (dupa cum specifica si enuntul temei: acestea au parametrii obligatorii,
dar si optionali) astfel ca am definit in aceste clase cate un constructor intern (MuseumBuilder si
LocationBuilder) care preia parametrii obligatorii si optionali ai celor doua clase si face:
1) un constructor public in care seteaza toti parametrii optionali la builderul respectiv
2) setteri specifici care returneaza referinta (this) la builder si seteaza campul optional respectiv la builder.
3) Va avea si metoda de build() care va returneaza un nou obiect de tipul Muzeu, repsectiv Location
folosind constructorii lor cu parametru de tip Builder care nu sunt expusi public, ci decat
clasei Muzeu/Location.

- Foarte important aici este modul in care ulterior in clasa de parsareFisierMuzeu construiesc entitate
Location si Muzeu utilizand builderul personalizat pe care l-am instantiat pe el prima data, apoi
am verificat fiecare camp optional preluat ca parametru daca exista sau nu( este sau nu null sau empty)
si am setat in builder in caz ca a existat. Apoi am putut avea Location-ul si Muzeul personalizat
dupa bunul plac.

## Design patternul Command:
- Am considerat ca este util sa induc comenzilor un nivel de "abstractizare" pentru utilizatorul
care doreste sa execute anumite actiuni: de adaugare a unui grup, de adaugare a unui ghid, respectiv 
membru la un grup, de adaugare muzeu, de cautat un ghid, de cautat un membru, de eliminat
un ghid/membru dintr un grup. Userul doar va crea clasa specifica de comanda si va apela execute()

- Fiecare comanda specifica va implementa interfata Comanda care prezinta o metoda de executie
execute() care poate arunca o exceptie de tipul Exception. Ulterior, fiecare comanda specifica va
avea propria implementare a metodei abstracte care vor arunca exceptii de tip GroupNotExists, majoritatea.
- Fiecare comanda specifica are un constructor cu parametrii, intrucat clasa comanda speficica va trebui sa
aiba acces(mai mult sau mai putin, de pinde fiecare comanda de ce parametrii doresc si am nevoie sa 
le dau) la un cod de muzeu si un timetable de care va avea nevoie sa preia un anumit grup din baza de date,
definit de acesti doi parametrii, vor mai avea unele si un parametru de tip Persoana pe care il voi
putea utiliza sa adaug un membru, sa adaug un ghid etc si un PrintWriter in care voi afisa outputul
diverselor actiuni. Excpetia `GrupNotExistsException` o voi arunca si o voi trata cand voi avea nevoie
de comanda specifica, si anume in clasele de parsare de fisiere, in functie de ce este primul argument
de pe fiecare linie.

## Design Patternul Observer:
- Acest design pattern comportamental l-am considerat mai mult decat necesar pentru a 
putea gestiona corect si eficient functionalitatea de adaugare de avenimente. Astfel, SUBIECTUL
in aceasta ecuatie este exact clasa Muzeu, careia i-am pus ca atribut privat o lista de observatori:
si anume `private List<Professor> observers = new ArrayList<Professor>()` adica o lista de ghizi
care atunci cand adaug un ghid la un grup (un grup avand automat si codul aferent muzeului
la care doreste sa mearga) am vrut sa inregistrez ghidul (de tip Person, dar facut cast
la Professor, intrucat daca adaugarea ghidului in grup a avut loc cu succes => nu s a aruncat exeptie
de tip GuideTypeException, deci stiu sigur ca este un Professor) la muzeul cu codul MuseumCode,
cu ajutorul metodei: `muzeuPentruAtasareObs.attachObserver((Professor) ghid);`
Observatorii vor fi efectiv fiecare ghid, care vor astepta ca atunci cand vad in clasa
ParsareFisierEvenimente, pe prima coloana `ADD EVENT`, voi repera muzeul cu codul aferent in baza de date
si apelez metoda de `setEvent(mesaj, pwEvent)`. Aici mesajul e extras din fisierul events.in
si PrintWriterul este fisierul de iesire care SE VA PROPAGA DIN METODA IN METODA, ca sa ajunga
emailul sa fie scris in fisier IN MOMENTUL IN CARE UN OBSERVATOR E NOTIFICAT.
- Fiecare entitate Person va implementa interfata Observer ce are metoda abstracta de update.
- In mod automat, cand se apeleaza metoda setEvent si se formeaza emailul in format corespunzator,
fiecare observator (ghid) din lista de observatori ai muzeului VA FI NOTIFICAT cu metoda
`notifyObserver(observer, mesajMail, pw);`. Metoda de notify va apela metoda observatorului observer
de updatare care la randul ei va scrie mailul in fisierul de IESIRE.

## Functionalitati extra:
- Am creat in clasa `Database` doua metode suplimentare:
`disponibilitateMuzeu()` in care pornesc de la premisa ca un muzeu poate fi vizitat in acelasi interval
orar timetable, doar de MAXIM 3 grupuri turistice, conform "ordinului interior al muzeului". In momentul
in care voi executa comanda de adaugare a unui grup in baza de date, cu un museumCode si un timetable
specific voi apela aceasta metoda din cadrul bazei de date pentru a permite adaugarea grupului,
numai si numai daca nu se depaseste limita maxima de 3 grupuri per interval. Metoda va arunca o exeptie
de tipul `MuseumFullException`, pe care o voi trata in cadrul comenzii ADD GUIDE, la adaugarea unui grup!

- Am mai creat o metoda `afisareMuzeeSortateDupaGrupuri()` prin care sa sortez muzeele astfel:
de la cele cu cele mai multe grupuri turistice care le-au vizitat, la muzeele cu cele mai putine vizite.
Nu iau in calcul cele care au zero vizite. M-am ajutat de un hashMap, pentru a retine pentru fiecare
muzeu(key) din baza de date corespondentul(valoarea): numarul de grupuri turistice care le-au calcat pragul.
Metoda returneaza o lista sortata, folosind un comparator anonim pe care o voi afisa daca in fisierul
groups, la parsarea din `ParsareFisierGroups` apare comanda pe linie ca prim argument: `ANALYSE MUSEUMS`.

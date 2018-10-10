{\rtf1\ansi\ansicpg1252\cocoartf1404\cocoasubrtf470
{\fonttbl\f0\fswiss\fcharset0 Helvetica;}
{\colortbl;\red255\green255\blue255;}
\paperw11900\paperh16840\margl1440\margr1440\vieww14360\viewh11180\viewkind0
\pard\tx566\tx1133\tx1700\tx2267\tx2834\tx3401\tx3968\tx4535\tx5102\tx5669\tx6236\tx6803\pardirnatural\partightenfactor0

\f0\i\b\fs28 \cf0 \ul \ulc0 README :\
\

\b0\fs24 Lancer le jeu :\
\pard\tx566\tx1133\tx1700\tx2267\tx2834\tx3401\tx3968\tx4535\tx5102\tx5669\tx6236\tx6803\pardirnatural\partightenfactor0

\i0 \cf0 \ulnone - Notre programme est compos\'e9 de 2 jeux. Le premier \'ab\'a0BikeGame\'a0\'bb et le jeu repr\'e9sentant le travail demand\'e9 dans la partie 5 du projet. Le second \'ab\'a0BikeGame2\'a0\'bb est le jeu contenant nos extension est modifications personnelles. Pour lancer le jeu ils vous suffit d\'92entrer le nom du jeu que vous voulez lancer (BikeGame ou BikeGame2) a la ligne 41 du fichier 
\b Program.java. 
\b0 Ensuite lancez (run) 
\b Program.java 
\b0 et le jeu se lancera. (Si vous voulez lancer un jeu autre comme ScaleGame, ContactGame etc.. alors vous avez juste a entrez le nom du jeu que vous voulez lancer a la ligne 41 et ensuite run le programme).\
\
\pard\tx566\tx1133\tx1700\tx2267\tx2834\tx3401\tx3968\tx4535\tx5102\tx5669\tx6236\tx6803\pardirnatural\partightenfactor0

\i \cf0 \ul Les controles :\
\pard\tx566\tx1133\tx1700\tx2267\tx2834\tx3401\tx3968\tx4535\tx5102\tx5669\tx6236\tx6803\pardirnatural\partightenfactor0

\i0 \cf0 \ulnone -Voici la liste des contr\'f4les et leurs effet :\
\pard\tx566\tx1133\tx1700\tx2267\tx2834\tx3401\tx3968\tx4535\tx5102\tx5669\tx6236\tx6803\pardirnatural\partightenfactor0

\b \cf0 1.
\b0  Fl\'e8che de gauche : applique une force angulaire (+) sur le v\'e9lo (permet de contr\'f4le son orientation / balancement)\

\b 2.
\b0  Fl\'e8che de droite : applique une force angulaire (-) sur le v\'e9lo (permet de contr\'f4le son orientation / balancement)\

\b 3.
\b0 Fl\'e8che du haut : permet d\'92activer le moteur et de donner la vitesse maximale a la roue motrice. Autrement dit cela g\'e8re l\'92acc\'e9l\'e9ration. Si vous appuyez et que la vitesse maximum est d\'e9j\'e0 atteinte alors rien ne se passera en particulier (le moteur sera \'ab\'a0relax\'a0\'bb).\

\b 4.
\b0  Fl\'e8che du bas : permet d\'92activer le moteur et de bloquer la vitesse de la roue motrice a 0. Autrement dit appuyer sur cette touche permet de freiner le v\'e9lo et de l\'92immobiliser.\

\b 5.
\b0  Touche espace : si vous appuyez dessus (une simple pression pas un appuie continue) l\'92orientation du v\'e9lo changera, la roue motrice sera alors chang\'e9 \'e9galement.\

\b 6. 
\b0 Touche R : Vous pouvez \'e0 tout moment appuyer sur la touche R afin de reset le jeu. Vous recommencer le niveau depuis la position de d\'e9part (au Start) et vos points et votre temps seront r\'e9initialis\'e9s a 0. (fonction reset classique)\

\b 7.
\b0  Touche C : SI et seulement SI vous avez pass\'e9 le checkpoint vous pourrez alors appuyez sur C a tout moment pour r\'e9apparaitre au checkpoint. Les points acquis apr\'e8s le checkpoint seront perdu et le temps ne sera pas r\'e9initialis\'e9. (Si vous avez fini le jeu vous ne pouvez pas recommencer depuis le checkpoint\'85).\
\
ATTENTION le checkpoint n\'92est pr\'e9sent que dans le jeu \'ab\'a0BikeGame2\'a0\'bb.  Ainsi la touche C n\'92aura aucun effet sur les autres jeux.\
\

\i \ul Les r\'e8gles :\
\pard\tx566\tx1133\tx1700\tx2267\tx2834\tx3401\tx3968\tx4535\tx5102\tx5669\tx6236\tx6803\pardirnatural\partightenfactor0

\i0 \cf0 \ulnone Vous trouverez ici un r\'e9capitulatif des r\'e8gles du jeu \'ab\'a0BikeGame2\'a0\'bb :\
\
- Les \'e9toiles rapportent 100 points suppl\'e9mentaire. Si vous passez le checkPoint est que vous n\'92avez pas r\'e9cup\'e9r\'e9 la premi\'e8re \'e9toile tant pis, elle n\'92est plus acc\'e9sible. Si vous r\'e9cup\'e9rez une \'e9toile apr\'e8s le checkpoint et que vous mourrez alors vous devrez de nouveau r\'e9cup\'e9rer l\'92\'e9toile pour avoir les points.\
\
- Les terrains \'ab\'a0glac\'e9\'a0\'bb sont des terrains ou la friction est tr\'e8s faible (terrains glissants). Ainsi vous ne pourrez pas contr\'f4ler votre v\'e9lo dessus. Du moins \'e7a sera plus difficile).\
\
- Si vous roulez sur les pics votre v\'e9lo sera immobilis\'e9 pendant 5sec. Ces 5secondes pass\'e9es, les pics ne seront plus actif et vous pourrez repartir.\
\
- Si vous tombez dans le feu vous mourrez.\
\
- une fois le parcours finit, si vous avez mit moins de 100secondes alors on ajoutera a votre score totale : [(100 - votreTemps)*10].\
\
- Si vous appuyez sur R vous recommencerez depuis le d\'e9but et votre score/temps seront initialis\'e9s a 0.\
\
- Si vous appuyez sur C et que vous aviez pass\'e9 le checkpoint alors vous recommencerai depuis ce dernier. Le score acquit apr\'e8s le passage du checkpoint sera reinitiais\'e9 mais pas le temps.\
\
PS : Il n\'92y a pas plusieurs niveau, donc aucune remarque a faire sur cette partie.\
\
}
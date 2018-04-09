<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<html>
<head>
	<meta http-equiv="content-type" content="text/html; charset=utf-8"/>
	<title></title>
	<meta name="generator" content="LibreOffice 5.4.5.1 (Linux)"/>
	<meta name="author" content="Jean-Louis PASTUREL"/>
	<meta name="created" content="2017-04-08T00:00:00.009383251"/>
	<meta name="changed" content="2018-03-28T08:35:51.282826049"/>
	<style type="text/css">
		h2.cjk { font-family: "SimSun" }
		h2.ctl { font-family: "Lucida Sans" }
	</style>
</head>
<body lang="en-GB" dir="ltr">
<h1 lang="fr-FR" style="page-break-before: always">Whatido&nbsp;:
Présentation du produit</h1>
<h2 lang="fr-FR" class="western">Généralités</h2>
<p lang="fr-FR">Il s'agit d'un utilitaire qui permet de visualiser
sur l'écran, les actions que l'on fait sur la souris ( clic /
droit/gauche/milieu et les actions avant / arrière sur la roulettes)
ainsi que les actions sur le clavier ( hors pavé numérique).</p>
<p lang="fr-FR">La visualisation se fait à travers 2 images
transparentes de la souris et du clavier qui reste en fenêtre
toujours visible ( sauf pour quelques menus contextuel où Windows
est prioritaire, mais ce n'est pas très gênant).</p>
<p lang="fr-FR">Le logiciel fonctionne aussi sur Linux et à priori
aussi sur OS X ( non testé par moi sur OS X, mapping du clavier à
faire), voir dans les annexes de ce document comment réaliser un
mapping complet si ce logiciel ne fonctionne pas correctement avec
votre micro/os/clavier. Il se peut qu'il y ait des écarts avec le
mapping Windows/AZERTY .</p>
<p style="margin-bottom: 0cm">Cette application est basée sur la
Bibliothèque <b>JNativeHook</b> disponible sur le site Github :
<a href="https://github.com/kwhat/jnativehook">https://github.com/kwhat/jnativehook</a></p>
<p style="margin-bottom: 0cm"><br/>

</p>
<p style="margin-bottom: 0cm">Le code binaire est disponible dans le
dépot Maven , j'ai utilisé la Version 2.1.0 : 
</p>
<p><a href="https://mvnrepository.com/artifact/com.1stleg/jnativehook/2.1.0">https://mvnrepository.com/artifact/com.1stleg/jnativehook/2.1.0</a></p>
<p>Une vidéo montrant whatido au travail :
<a href="https://youtu.be/UVeElF26KB8">https://youtu.be/UVeElF26KB8</a></p>
<h2 class="western">Un aperçu du produit sur ce document</h2>
<p>L'écran de lancement qui sera expliqué en détail plus loin dans
le document.</p>
<p align="center"><img src="readme_FR_md_m665efb61.jpg" name="images1" align="bottom" width="603" height="377" border="0"/>
</p>
<p><br/>
<br/>

</p>
<p style="page-break-before: always">Une image du produit en action&nbsp;:</p>
<p align="center"><img src="readme_FR_md_m12bd0ad0.jpg" name="images2" align="bottom" width="642" height="366" border="0"/>
</p>
<p lang="fr-FR">On voit un rond bleu sur la touche Print-Screen quand
j'ai fait la copie d'écran&nbsp;!</p>
<p lang="fr-FR">On peut choisir l'affichage ou non du clavier, on
peut activer le son qui décrit les actions faites sur la souris et
le clavier. Pour le clavier on dispose de 3 tailles ( Normal, Medium,
Small), utile pour l'apprentissage du clavier aux débutants .</p>
<p lang="fr-FR">Pour ce qui concerne la souris, on a 4 tailles au
choix ( Large, Medium, Small, Tiny) et le fait d’être mobile pour
les 3 plus petites tailles et d'être fixe pour les 3 plus grandes
tailles.</p>
<p lang="fr-FR">Pour les souris de type fixe, le bouton noir au
milieu permet de la déplacer dans une autre partie de l'écran quand
elle gène.</p>
<h2 class="western">Exemples d'utilisation du produit</h2>
<p><b>Whatido </b>fonctionne correctement avec des vidéo-projecteurs.</p>
<p>Les utilisations possibles sont :</p>
<ul>
	<li/>
<p>initiation à l'informatique pour la présentation des
	actions souris et le clavier</p>
	<li/>
<p>présentation en direct de logiciel en visualisant toutes
	les actions souris et clavier</p>
	<ul>
		<ul>
			<li/>
<p>ex : navigation dans l'explorateur de fichier, mécanismes
			du copier/coller, utilisation des outils bureautiques ...</p>
		</ul>
	</ul>
	<li/>
<p>création de tutoriels vidéo par enregistrement de l'écran
	avec <b>whatido</b> activé.</p>
</ul>
<p>Astuce : on peut lancer 2 fois le produit :</p>
<ul>
	<ul>
		<li/>
<p>une fois avec une souris fixe de taille Large ou Médium</p>
		<li/>
<p>l'autre fois avec la souris Tiny/Mobile qui va suivre le
		curseur Windows au cours de ses déplacements. 
		</p>
	</ul>
</ul>
<p>Les 2 souris montreront les actions faites.</p>
<p>Limitation avec <b>Powerpoint</b> en mode Diaporama, le produit
(image souris et/ou image clavier) n’apparaît pas en premier plan
et ne peut être utiliser dans ce cas, il faut rester en mode
édition.</p>
<h1>Installation</h1>
<h2 class="western">Pré-requis</h2>
<p><b>Whatido</b> nécessite la présence d'une machine virtuelle
<b>Java </b>récente version supérieure à <b>1.8.0_121</b>.</p>
<p>On pourra installer la version <b>JRE 32 bits</b> depuis le site
de <b>Oracle</b>&nbsp;:</p>
<p><a href="http://www.oracle.com/technetwork/java/javase/downloads/jre8-downloads-2133155.html">http://www.oracle.com/technetwork/java/javase/downloads/jre8-downloads-2133155.html</a></p>
<p align="center"><img src="readme_FR_md_m464ab979.jpg" name="images3" align="bottom" width="465" height="214" border="0"/>
</p>
<p><br/>
<br/>

</p>
<p>Cocher l'acceptation de licence.</p>
<p align="center"><img src="readme_FR_md_m7cfa0b96.jpg" name="images4" align="bottom" width="571" height="358" border="0"/>
</p>
<p lang="fr-FR">Choisir le téléchargement de la version 32 bits (
i586) . La version à télécharger sur le site de Oracle doit être
la plus récente.</p>
<p><span lang="fr-FR">Après téléchargement, il faudra lancer le
</span><span lang="fr-FR"><b>fichier .exe correspondant en mode
administrateur</b></span> <span lang="fr-FR">( clic droit sur le
fichier, et choisir lancement en mode administrateur).</span></p>
<p lang="fr-FR">Une fois installé, vous devez pouvoir voir votre JRE
comme montré ci-dessous&nbsp;:</p>
<p align="center"><img src="readme_FR_md_609d63a0.jpg" name="images5" align="bottom" width="645" height="408" border="0"/>
</p>
<p><br/>
<br/>

</p>
<h2 class="western">Installation de Whatido</h2>
<p>Le produit se présente sous la forme d'une archive zip
<b>whatido&lt;Version&gt;Exe.zip</b> ex <b>whatido3Exe.zip </b><span style="font-weight: normal">.
Une archive au format zip est disponible sous la racine de :</span></p>
<p><a href="https://github.com/PASTJL/whatido3"><span style="font-weight: normal">https://github.com/PASTJL/whatido3</span></a></p>
<p><span style="font-weight: normal">On va supposer pour la suite de
l'installation qu'il existe un répertoire </span><b>C:\opt</b> <span style="font-weight: normal">sur
votre ordinateur, sinon vous le créez ou bien vous adapterez la
procédure décrite ci-dessous.</span></p>
<ul>
	<li/>
<p><span style="font-weight: normal">Positionner l'archive
	</span><b>whatido3Exe.zip</b> <span style="font-weight: normal">sous
	</span><b>C:\opt</b> <span style="font-weight: normal">et dézipper</span></p>
	<li/>
<p><span style="font-weight: normal">Ouvrir le fichier
	C:\opt\whatido\script\</span><b>whatido.cmd</b> <span style="font-weight: normal">et
	adapter les chemins en rouge </span>
	</p>
</ul>
<p style="margin-left: 1.25cm; background: #ccffff"><font face="Consolas, monospace"><font size="2" style="font-size: 10pt"><span style="font-weight: normal">Set
PROJECT_HOME=</span><font color="#ff0000"><b>C:\opt\whatido</b></font></font></font></p>
<p style="margin-left: 1.25cm; font-weight: normal; background: #ccffff">
<font face="Consolas, monospace"><font size="2" style="font-size: 10pt">Set
CLASSPATH=%PROJECT_HOME%\lib\jnativehook-2.1.0.jar;.;%PROJECT_HOME%\lib\whatido-3.0.0.jar;</font></font></p>
<p style="margin-left: 1.25cm; background: #ccffff"><font face="Consolas, monospace"><font size="2" style="font-size: 10pt"><span style="font-weight: normal">Set
JAVA_HOME=C:\Program Files (x86)\Java\</span><font color="#ff0000"><b>jre1.8.0_121</b></font><span style="font-weight: normal">\bin</span></font></font></p>
<p style="margin-left: 1.25cm; font-weight: normal; background: #ccffff">
<font face="Consolas, monospace"><font size="2" style="font-size: 10pt">start
&quot;&quot; &quot;%JAVA_HOME%\javaw&quot; -Droot=%PROJECT_HOME%
-Dhome=%PROJECT_HOME% -cp %CLASSPATH% com.jlp.whatido.Main </font></font>
</p>
<p style="margin-left: 1.25cm; font-weight: normal; background: #ccffff">
<font face="Consolas, monospace"><font size="2" style="font-size: 10pt">Exit</font></font></p>
<ul>
	<li/>
<p><span style="font-weight: normal">Ouvrir le fichier
	C:\opt\whatido\script\</span><b>testingMouseKeyBoard.cmd</b> <span style="font-weight: normal">et
	adapter les chemins en rouge </span>
	</p>
</ul>
<p style="margin-left: 2.5cm; background: #ccffff; page-break-before: always">
<font face="Consolas, monospace"><font size="2" style="font-size: 10pt"><span style="font-weight: normal">Set
PROJECT_HOME=</span><font color="#ff0000"><b>C:\opt\whatido</b></font></font></font></p>
<p style="margin-left: 2.5cm; font-weight: normal; background: #ccffff">
<font face="Consolas, monospace"><font size="2" style="font-size: 10pt">Set
CLASSPATH=%PROJECT_HOME%\lib\jnativehook-2.1.0.jar;.;%PROJECT_HOME%\lib\whatido-3.0.0.jar;</font></font></p>
<p style="margin-left: 2.5cm; background: #ccffff"><font face="Consolas, monospace"><font size="2" style="font-size: 10pt"><span style="font-weight: normal">Set
JAVA_HOME=C:\Program Files (x86)\Java\</span><font color="#ff0000"><b>jre1.8.0_121</b></font><span style="font-weight: normal">\bin</span></font></font></p>
<p style="margin-left: 2.5cm; font-weight: normal; background: #ccffff">
<font face="Consolas, monospace"><font size="2" style="font-size: 10pt">&quot;%JAVA_HOME%\java&quot;
-Droot=%PROJECT_HOME% -Dhome=%PROJECT_HOME% -cp %CLASSPATH%
com.jlp.whatido.MyMouseKeyboardListener </font></font>
</p>
<ul>
	<li/>
<p style="background: transparent"><font face="Times New Roman, serif"><font size="3" style="font-size: 12pt"><span style="font-weight: normal">Faire
	un raccourci sur le bureau pour le fichier
	C:\opt\whatido\script\</span><b>whatido.cmd</b></font></font></p>
	<li/>
<p style="font-weight: normal; background: transparent"><font face="Times New Roman, serif"><font size="3" style="font-size: 12pt">Une
	icône <b>whatidoIco.ico</b> est fournie dans le répertoire
	<b>C:\opt\whatido\script\ </b>pour illustrer le raccourci.</font></font></p>
	<li/>
<p style="background: transparent"><font face="Times New Roman, serif"><font size="3" style="font-size: 12pt"><span style="font-weight: normal">Cliquer
	sur le raccourci pour lancer</span> <b>whatido.</b></font></font></p>
</ul>
<p style="background: transparent"><br/>
<br/>

</p>
</body>
</html>
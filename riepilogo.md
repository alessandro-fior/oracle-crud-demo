# Riepilogo dei comandi per testare l’esempio **HelloX** in JDeveloper

## 1. Copiare i file di esempio in JDeveloper

> Sostituisci i percorsi con quelli reali della tua installazione di JDeveloper. L’esempio è destinato a **JDeveloper 12.2.1.3.42** (o ultima 12.2 disponibile).

```powershell
# Percorso delle unità di JDeveloper
$jdevoPath = 'C:\Program Files\Oracle\JDeveloper\12.2.1.3.42'
# Folder dove verrà installato il plugin
$targetPlugin = Join-Path $jdevoPath 'plugins\HelloX'

# Crea la cartella ‘plugins\HelloX’ se non esiste
New-Item -ItemType Directory -Path $targetPlugin -Force

# Copia tutti i file (jpr, src, risorse) dal progetto esterno
robocopy "C:\projects\extension-samples-12.2.1.3.42.170820.914\HelloX" \
        $targetPlugin /E
```

> Se `robocopy` non è disponibile, usa `xcopy`:
>
> `xcopy "C:\projects\extension-samples-12.2.1.3.42.170820.914\HelloX\*.*" "$targetPlugin\" /s /e /h`

## 2. Costruisci il plugin (facoltativo)

```powershell
# Se nel folder c’è un pom.xml
Push-Location -Path $targetPlugin
mvn clean package
Pop-Location
```

> In genere JDeveloper costruirà il plugin automaticamente a runtime.

## 3. Riavvia JDeveloper

```powershell
& "$jdevoPath\jdeveloper.exe"
```

## 4. Verifica l’estensione in JDeveloper

1. Apri JDeveloper.
2. Vai a **Tools → New… → Wizard**.
3. Seleziona **HelloX (ESDK Sample)**.
4. Clicca **Create**.
5. Inserisci un nome nella finestra che appare e premi **OK**.
6. Dovrà comparire un messaggio *"Hello <nome>!*.

## 5. Debug in JDeveloper (facoltativo)

- Apri `HelloX.java`.
- Metti un breakpoint in `invoke()`.
- Avvia la modalità **Debug** dal menu **Debug → Debug**.

> I comandi sopra presuppongono l’uso di PowerShell su Windows. Se usi un altra shell, adatta i comandi di copia (`cp`, `cp -r`, ecc.).

---

### Note

- L’esempio è una **Wizard** di JDeveloper, non si esegue come una semplice applicazione Java.
- Se non hai JDeveloper 12.2.1.3.42, installane una versione compatibile o adatta i percorsi.
- Se trovi errori di compilazione, verifica che la cartella `plugins` di JDeveloper sia accessibile in scrittura e che non ci siano conflitti di nome.
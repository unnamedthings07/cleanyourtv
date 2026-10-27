# Install a ready-made launcher APK (for example the one built by the "Build APK" GitHub Action) on the TV.
# Usage: tools\install-apk.ps1 -TvHost 192.168.1.50 -Apk C:\path\to\app-release.apk
# Afterwards push the theme with tools\theme.ps1 (it also restarts Home).
param(
    [string]$TvHost = $env:TV_HOST,
    [Parameter(Mandatory = $true)][string]$Apk
)
$ErrorActionPreference = 'Stop'
$OutputEncoding = New-Object System.Text.UTF8Encoding $false
if (-not $TvHost) { throw 'pass -TvHost <tv ip> or set $env:TV_HOST' }
$env:TV_HOST = $TvHost
$Apk = (Resolve-Path $Apk).Path
Set-Location $PSScriptRoot
$py = 'venv\Scripts\python.exe'
if (-not (Test-Path $py)) { throw 'run: python -m venv tools\venv; tools\venv\Scripts\python.exe -m pip install -r tools\requirements.txt' }
& $py -I b64push.py $Apk /data/local/tmp/tvhome.apk
if ($LASTEXITCODE -ne 0) { throw 'APK upload failed' }
$out = & $py -I tv.py 'pm install -r /data/local/tmp/tvhome.apk; rm -f /data/local/tmp/tvhome.apk'
Write-Host $out
if ("$out" -notmatch 'Success') { throw 'pm install failed' }
Write-Host 'Installed. Now push the theme: tools\theme.ps1 -TvHost <ip> themes\prabhas'

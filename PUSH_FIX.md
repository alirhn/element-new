# Fix: Push to New Repo (Shallow Clone Error)

Your repo is a **shallow clone**. Fix by fetching full history from the original repo, then pushing again.

## Steps (run in project root)

### 1. Add the original Element repo as a temporary remote
```bash
git remote add upstream https://github.com/element-hq/element-x-android.git
```

### 2. Fetch full history (this may take a few minutes)
```bash
git fetch --unshallow upstream
```
If that fails (e.g. "fatal: --unshallow on a complete repo does not make sense"), your clone might already be unshallow; try:
```bash
git fetch upstream main
```

### 3. Remove the temporary remote (optional)
```bash
git remote remove upstream
```

### 4. Push to your new repo
```bash
git push -u origin main
```

---

## Alternative: Push only current state (no history)

If you **don't care about preserving full history** and just want the current code on `element-new`:

```bash
# Create a fresh repo with only the current tree (one commit)
rm -rf .git
git init
git remote add origin git@github.com:alirhn/element-new.git
git add -A
git commit -m "Initial commit: element-x-android"
git branch -M main
git push -u origin main
```

**Warning:** This removes all existing commit history. Use only if you're sure you don't need it.

# Plagiarism Detector (N-gram Overlap)

End-to-end CLI tool that flags pairs of documents with suspicious overlap by counting shared **n-word contiguous phrases**. Capable of analyzing **1000+ documents** with each other in under **2 minutes**.

**Author:** Saavan Kiran  
**Language:** Java (standard library only)  
**Entry point:** `PlagiaristCatcher`

---

## What I built (for recruiters)

- Designed and shipped a complete text-similarity pipeline: ingest → normalize → n-gram generation → pairwise scoring → ranked results.
- Implemented two comparison strategies: a fast **HashSet union** method and an alternative **sorted-merge** counter.
- Scaled cleanly with progress instrumentation and simple UX prompts.
- Wrote the whole project from scratch using core data structures/algorithms (hash sets, binary/merge logic, custom sorting).

---

## Features

- **Phrase length `n ≥ 1`** — user-selectable.
- **Pairwise comparisons** across all `.txt` files in a chosen dataset folder.
- **Hit threshold** — show only pairs at or above your cutoff.
- **Ranked output** — highest hit counts first.
- **Zero external deps** — plain Java, easy to compile and run anywhere.

---

## How it works

1. **Dataset selection**  
   At launch, choose a dataset folder (e.g., *Small / Medium / Large* sets of `.txt` files).

2. **Parameters**  
   - **Phrase length** `n` (≥ 1)  
   - **Minimum hits** to report (≥ 1)

3. **Phrase generation** (`FileReader`)  
   - Loads each `.txt`, lower-cases words, strips non-letters, and emits **every contiguous n-word phrase** via staggered offsets.

4. **Pairwise scoring** (`HitList`)  
   - **Default (fast):** HashSet overlap using  
     ```
     hits = |setA| + |setB| - |union(setA, setB)|
     ```
   - **Alternate (reference):** Sorted-merge counter (`HitCounter`) over ordered phrase lists.

5. **Output**  
   - Prints lines like: [fileA.txt , fileB.txt] -> hitCount
   - ordered by descending hit count.

---

## Repo at a glance
```
src/
  PlagiaristCatcher.java  # CLI and parameter prompts
  HitList.java            # orchestrates phrase generation & pairwise ranking
  HitCounter.java         # optional sorted-merge counter (alternate method)
  FileReader.java         # reads .txt files and emits n-gram phrases
data/                     # optional small sample docs (not committed)
```

---

## Implementation Notes

- Normalization: Non-letters are stripped and words are lower-cased before building phrases.
- Performance:
  - The HashSet method is near-linear in the number of phrases per file and generally faster than sorting/merging for class-sized corpora.
  - The sorted-merge approach (HitCounter) remains in the code as a reference; it requires sorted phrase lists.
- Deterministic ordering: File names are bubble-sorted once in FileReader to ensure stable pair order.
- Progress logs: For large datasets you’ll see periodic setup/comparison counters.

---

## Limitations & future improvements

- Stopwords & stemming: Currently counts all words; adding a stopword filter or stemmer would reduce false positives on boilerplate text.
- Punctuation & Unicode: Strips non-A–z characters; consider widening to full Unicode letters.
- Memory: For very large corpora, switch to streaming or probabilistic similarity instead of exact set overlaps.
- Optional path toggle: The sorted-merge comparator is present but unused by default; adding a CLI flag would let users choose strategies.

---

## Example output
```
Completed hit list (minimum of 12 hits of 4 word contiguous phrases):
[file03.txt , file17.txt]   ->   37
[file12.txt , file27.txt]   ->   29
[file02.txt , file08.txt]   ->   18
...
```

---

## License

This repository is for educational use. If you fork or adapt it, please credit me.

# htmx-playground

As much a way of learning as a tool

## Usage

Small Kanban like app, using htmx and clojure ring. 

Data will be stored in a key-value store. There is no schema. Columns are defined by searches and a search
can be for combination of key-value-operator pairs. for example, (:name :contains :test) or (:date :after "20230324"). 

Roadmap:
  - search critera can be combined
  - combinations can be nested
  - changes to resultsets are pushed to the dashboard (no need to refresh)
  

## Technologies

 - [HMLX](https://htmx.org/)
 - [Aleph](https://aleph.io/manifold/rationale.html) and [manifold](https://github.com/clj-commons/manifold) for streaming card updates to the dashboards

## License

MIT License

Copyright (c) 2023 Ward Coessens

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
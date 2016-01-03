SHELL := /bin/bash

all: relatorio.pdf clean 
	@echo "Possíveis Resultados"
	@echo " make relatorio.pdf"
	@echo " make clean"

recompile: removepdf relatorio.pdf clean

removepdf:
	rm -f relatorio.pdf

clean: 
	rm -rf *.toc
	rm -rf *.aux
	rm -rf *.log
	rm -rf *.fls
	rm -rf *.fdb_latexmk
	rm -f report/*.aux 
	rm -f report/*.toc
	rm -f report/*.log
	rm -f report/chapters/*.aux


relatorio.pdf:  report/rel.tex
	latexmk -pdf report/rel.tex
	mv rel.pdf relatorio.pdf
	rm -fr rel.* 
	rm -fr report/chapters/*.aux


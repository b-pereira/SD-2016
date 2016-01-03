JFLAGS = -g -d
JC = javac
.SUFFIXES: .java .class
SRCDIR      = src
OUTDIR      = bin/
.java.class:
	$(JC) $(JFLAGS) ${OUTDIR} -cp ${OUTDIR} -sourcepath ${SRCDIR} ${SRCDIR}/sd1516/Cliente.java
	$(JC) $(JFLAGS) ${OUTDIR} -cp ${OUTDIR} -sourcepath ${SRCDIR} ${SRCDIR}/sd1516/Servidor.java
	$(JC) $(JFLAGS) ${OUTDIR} -cp ${OUTDIR} -sourcepath ${SRCDIR} ${SRCDIR}/sd1516_Testes/Teste.java

CLASSES = \
	$(SRCDIR)/sd1516/business/Dados.java \
	$(SRCDIR)/sd1516/business/Passageiro.java \
	$(SRCDIR)/sd1516/business/Taxista.java \
	$(SRCDIR)/sd1516/business/Taxista.java \
	$(SRCDIR)/sd1516/threads/EscutaPedido.java \
	$(SRCDIR)/sd1516/utils/DadosTransito.java \
	$(SRCDIR)/sd1516/utils/Posicao.java \
	$(SRCDIR)/sd1516_Testes/Teste.java \
	$(SRCDIR)/sd1516/Cliente.java \
	$(SRCDIR)/sd1516/Servidor.java 


default: classes

classes: $(CLASSES:.java=.class)

clean:
	find . -name "*.class" -type f -delete
	$(RM) -r ${OUTDIR}

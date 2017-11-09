package br.com.sankhya;

import javax.mail.MessagingException;

import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
//teste git
public class MainClass
{

	static StringBuffer mensagem = new StringBuffer();
	static BigDecimal nunota=BigDecimal.ZERO;
	static int ramalVend=0;
	static String nomeVend="";

	public static void getPDFData(byte[] arquivo, String nome) 
	{

		byte[] fileBytes;
		try
		{
			fileBytes = arquivo;
			OutputStream targetFile=  new FileOutputStream(
					"/users/adriano/relatorios/propvenda/"+nome+".pdf");
			targetFile.write(fileBytes);
			targetFile.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}	

	public static Object[] ExecutaComandoNoBanco(String sql, String op)
	{
		try
		{  Object[] objRetorno=new Object[10];
		int cont=0; 
		Statement smnt = ConnectMSSQLServer.conn.createStatement(); 

		if(op=="select")
		{
			smnt.execute(sql);
			ResultSet result = smnt.executeQuery(sql); 

			while(result.next())
			{
				objRetorno[cont]=result.getObject(1);
				cont++;
			}
			return objRetorno;
		}
		else if(op=="alter")
		{
			smnt.executeUpdate(sql);
			objRetorno[0]=(Object)1;
			return objRetorno;
		}
		else
		{
			return null;
		}
		}
		catch(SQLException ex)
		{
			System.err.println("SQLException: " + ex.getMessage());
			mensagem.append("Erro ao obter campo SQL("+ex.getMessage()+") \n");
			return null;
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static void main(String[] args) 
	//@Override
	//public void doAction(ContextoAcao contexto) throws Exception
	{
		MailJava mj = new MailJava();

		String emailParc="", emailVend="", nomeParc="", dhEnvio="";
		BigDecimal tipoOp=BigDecimal.ZERO, codParc=BigDecimal.ZERO, 
				codVend=BigDecimal.ZERO, codContato=BigDecimal.ZERO;
		int codEnvioDocsMedika=1;


		mensagem.setLength(0);

		//Conecta no banco do Sankhya
		ConnectMSSQLServer.dbConnect("jdbc:sqlserver://192.168.0.5:1433;DatabaseName=SANKHYA_TREINA;", "adriano","Compiles23");

		//recupera o numero da negociação
		try
		{
			//			Registro[] linha = contexto.getLinhas();
			//
			//			for (int i = 0; i < linha.length; i++) 
			//			{
			//
			//				nunota= (BigDecimal)linha[i].getCampo("NUNOTA");
			//				tipoOp=(BigDecimal) linha[i].getCampo("CODTIPOPER");
			//				codParc=(BigDecimal) linha[i].getCampo("CODPARC");
			//				codVend=(BigDecimal) linha[i].getCampo("CODVEND");
			//				codContato=(BigDecimal) linha[i].getCampo("CODCONTATO");
			//				emailCopia=(String)linha[i].getCampo("AD_COPIAEMAILPROP");
			//				emailCopiaOculta=(String)linha[i].getCampo("AD_COPIA2EMAILPROP");
			//				aprovado=(String)linha[i].getCampo("APROVADO");
			//			}
		}catch(Exception e)
		{
			mensagem.append("Erro ao obter campos sankhya. "+e.getMessage());
			//	contexto.setMensagemRetorno(mensagem.toString());
			System.out.println(mensagem);
		}

		//Recupera o nome do perceiro
		//			if(ExecutaComandoNoBanco("SELECT NOMEPARC FROM TGFPAR WHERE CODPARC=4837"
		//					/*+codParc.toString()*/, "select")!=null)
		//			{
		//				nomeParc=(String) ExecutaComandoNoBanco("SELECT NOMEPARC FROM TGFPAR WHERE CODPARC=4837"
		//						/*+codParc.toString()*/, "select");
		//			}
		nomeParc="teste";

		//			//Recupera o ramal do vendedor
		//			if(ExecutaComandoNoBanco("SELECT AD_RAMAL FROM TGFVEN WHERE CODVEND="
		//					+codVend.toString(), "select")!=null)
		//			{
		//				ramalVend=(Integer) ExecutaComandoNoBanco("SELECT AD_RAMAL FROM TGFVEN WHERE CODVEND="
		//						+codVend.toString(), "select");
		//			}


		//Recupera o email do vendedor
		//			if(ExecutaComandoNoBanco("SELECT EMAIL FROM TSIUSU WHERE CODUSU="
		//					+contexto.getUsuarioLogado().toString(), "select")!=null)
		//			{
		//				emailVend=(String) ExecutaComandoNoBanco("SELECT EMAIL FROM TSIUSU WHERE CODUSU="
		//						+contexto.getUsuarioLogado().toString(), "select");
		//			}
		emailVend="adriano.soares@medika.com.br";

		//Recupera o nome do vendedor
		//			if(ExecutaComandoNoBanco("SELECT FUN.NOMEFUNC FROM TGFVEN VEN"+
		//					" INNER JOIN TSIUSU USU ON USU.CODVEND = VEN.CODVEND"+
		//					" INNER JOIN TFPFUN FUN ON FUN.CODFUNC = USU.CODFUNC AND FUN.CODEMP=3"+
		//					" WHERE  USU.CODVEND="+codVend.toString(), "select")!=null)
		//			{
		//				nomeVend=(String) ExecutaComandoNoBanco("SELECT FUN.NOMEFUNC FROM TGFVEN VEN"+
		//						" INNER JOIN TSIUSU USU ON USU.CODVEND = VEN.CODVEND"+
		//						" INNER JOIN TFPFUN FUN ON FUN.CODFUNC = USU.CODFUNC AND FUN.CODEMP=3"+
		//						" WHERE  USU.CODVEND="+codVend.toString(), "select");
		//			}
		nomeVend="Adriano";

		//			//Recupera o email do parceiro
		//			if(ExecutaComandoNoBanco("SELECT CTT.EMAIL FROM TGFCAB CAB"
		//					+ " INNER JOIN TGFCTT CTT ON CTT.CODCONTATO=CAB.CODCONTATO"
		//					+ " AND CTT.CODPARC=CAB.CODPARC"
		//					+ " WHERE CAB.CODCONTATO="+codContato.toString()+" AND CAB.CODPARC="
		//					+codParc.toString(), "select")!=null)
		//			{
		//				emailParc=(String) ExecutaComandoNoBanco("SELECT CTT.EMAIL FROM TGFCAB CAB"
		//						+ " INNER JOIN TGFCTT CTT ON CTT.CODCONTATO=CAB.CODCONTATO"
		//						+ " AND CTT.CODPARC=CAB.CODPARC"
		//						+ " WHERE CAB.CODCONTATO="+codContato.toString()+" AND CAB.CODPARC="
		//						+codParc.toString(), "select");
		//			}

		emailParc="gadrianosl@gmail.com";

		if(ExecutaComandoNoBanco("SELECT DHENVIO FROM AD_ENVIODOCSMEDIKA "
				+ " WHERE CODENVIODOCSMEDIKA="+codEnvioDocsMedika, "select")[0]!=null)
		{
			dhEnvio=ExecutaComandoNoBanco("SELECT DHENVIO FROM AD_ENVIODOCSMEDIKA "
					+ " WHERE CODENVIODOCSMEDIKA="+codEnvioDocsMedika, "select")[0].toString();	
		}

		if(dhEnvio=="")
		{

			//configuracoes de envio
			mj.setSmtpHostMail("email-ssl.com.br");
			mj.setSmtpPortMail("465");
			mj.setSmtpAuth("true");
			mj.setSmtpStarttls("true");
			mj.setUserMail(emailVend);
			mj.setFromNameMail(nomeVend);
			mj.setSmtpAuth("adrianomedika");
			mj.setPassMail("adrianomedika");
			mj.setCharsetMail("ISO-8859-1");
			mj.setSubjectMail("Documentação Medika.");
			mj.setBodyMail(htmlMessage());
			mj.setTypeTextMail(MailJava.TYPE_TEXT_HTML);

			//sete quantos destinatarios desejar
			String dest=emailParc;
			Object[] destinatarios=new Object[10];

			if(ExecutaComandoNoBanco("SELECT CTT.EMAIL FROM AD_EMAILSENVIODOCSMEDIKA ADE "
					+ "INNER JOIN TGFCTT CTT ON (CTT.CODPARC=ADE.CODPARC AND CTT.CODCONTATO=ADE.CODCONTATO) "
					+ " WHERE CODENVIODOCSMEDIKA="+codEnvioDocsMedika, "select")!=null)
			{
				destinatarios=ExecutaComandoNoBanco("SELECT CTT.EMAIL FROM AD_EMAILSENVIODOCSMEDIKA ADE "
						+ "INNER JOIN TGFCTT CTT ON (CTT.CODPARC=ADE.CODPARC AND CTT.CODCONTATO=ADE.CODCONTATO) "
						+ " WHERE CODENVIODOCSMEDIKA="+codEnvioDocsMedika, "select");	
			}

			String[] stDestinatarios=new String[10];
			for(int i=0;i<destinatarios.length;i++)
			{
				if(destinatarios[i]!=null)
				{
					stDestinatarios[i]=destinatarios[i].toString().trim();
				}
			}
			stDestinatarios[0]="gadrianosl@gmail.com";
			mj.setToMailsUsers(stDestinatarios);

			Object[] retorno;
			Object[] nomes=new Object[10];
			//seta quatos anexos desejar
			List files = new ArrayList();

			//Seleciona os arquivos para anexar
			if(ExecutaComandoNoBanco("SELECT ADM.ARQUIVO FROM AD_ENVIODOCSMEDIKA ADE"
					+ " INNER JOIN AD_ANEXOSENVIODOCSMEDIKA ADA "
					+ " ON ADA.CODENVIODOCSMEDIKA=ADE.CODENVIODOCSMEDIKA"
					+ " INNER JOIN AD_DOCSMEDIKA ADM ON ADM.CODDOCSMEDIKA=ADA.CODDOCSMEDIKA"
					+ " WHERE ADE.CODENVIODOCSMEDIKA="+codEnvioDocsMedika, "select")!=null)
			{
				retorno=ExecutaComandoNoBanco("SELECT ADM.ARQUIVO FROM AD_ENVIODOCSMEDIKA ADE"
						+ " INNER JOIN AD_ANEXOSENVIODOCSMEDIKA ADA "
						+ " ON ADA.CODENVIODOCSMEDIKA=ADE.CODENVIODOCSMEDIKA"
						+ " INNER JOIN AD_DOCSMEDIKA ADM ON ADM.CODDOCSMEDIKA=ADA.CODDOCSMEDIKA"
						+ " WHERE ADE.CODENVIODOCSMEDIKA="+codEnvioDocsMedika, "select");

				//Seleciona os nomes dos arquivos para os anexos
				if(ExecutaComandoNoBanco("SELECT ADM.DESCRICAO FROM AD_ENVIODOCSMEDIKA ADE"
						+ " INNER JOIN AD_ANEXOSENVIODOCSMEDIKA ADA "
						+ " ON ADA.CODENVIODOCSMEDIKA=ADE.CODENVIODOCSMEDIKA"
						+ " INNER JOIN AD_DOCSMEDIKA ADM ON ADM.CODDOCSMEDIKA=ADA.CODDOCSMEDIKA"
						+ " WHERE ADE.CODENVIODOCSMEDIKA="+codEnvioDocsMedika, "select")!=null)
				{
					nomes=ExecutaComandoNoBanco("SELECT ADM.DESCRICAO FROM AD_ENVIODOCSMEDIKA ADE"
							+ " INNER JOIN AD_ANEXOSENVIODOCSMEDIKA ADA "
							+ " ON ADA.CODENVIODOCSMEDIKA=ADE.CODENVIODOCSMEDIKA"
							+ " INNER JOIN AD_DOCSMEDIKA ADM ON ADM.CODDOCSMEDIKA=ADA.CODDOCSMEDIKA"
							+ " WHERE ADE.CODENVIODOCSMEDIKA="+codEnvioDocsMedika, "select");	
				}

				for(int i=0; i<retorno.length;i++)
				{
					if (retorno[i]!=null)
					{
						getPDFData((byte[])retorno[0], nomes[i].toString());
						files.add("/users/adriano/relatorios/propvenda/"+nomes[i].toString()+".pdf");
					}
				}

			}

			mj.setFileMails(files);

			try {
				new MailJavaSender().senderMail(mj);

				mensagem.append("Email enviado com suscesso!");
				System.out.println(mensagem);

				ExecutaComandoNoBanco("UPDATE AD_ENVIODOCSMEDIKA SET DHENVIO=GETDATE() WHERE CODENVIODOCSMEDIKA="+codEnvioDocsMedika, "alter");

			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			} catch (MessagingException e) {
				e.printStackTrace();
			}
		}
		else
		{
			mensagem.append("Envio já executado!");
			System.out.println(mensagem);
		}

	}

	private static String htmlMessage() {
		return
				"<html><head> <meta charset="+"\"UTF-8"+"\"></head>"
				+ "<body style="+"\"font-famaly: arial; font-size:12px;"+"\">Prezado(s),<br/><br/>"+		             
				"Segue a documentação solicitada.<br/><br/>"+

				"<br/><br/>"+

				"Atenciosamente,"+
				"<br/><br/>"+nomeVend+
				" - Tel:(31) 3688-1901 Ramal:"+ramalVend+" - Equipe de Vendas"+
				"<br><br><HR WIDTH=100% style="+"\"border:1px solid #191970;"+
				"\"><img src="+"\"http://www.medika.com.br/wp-content/uploads/2016/05/logo-medika.png"+
				"\"><br><br>Medika, qualidade em saúde. - <a href="+"\"http://www.medika.com.br"+
				"\">www.medika.com.br</a><br>"+
				"<HR WIDTH=100% style="+"\"border:1px solid #191970;"+"\">"+
				"</body></html>";
	}
}

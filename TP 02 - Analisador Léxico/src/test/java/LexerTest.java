import error.CompilerError;
import io.vavr.collection.List;
import java_cup.runtime.Symbol;
import org.assertj.core.api.JUnitSoftAssertions;
import org.junit.Rule;
import org.junit.Test;
import parse.Lexer;
import parse.Terminals;

import java.io.IOException;
import java.io.StringReader;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertArrayEquals;

public class LexerTest {

   private String run(String input) throws IOException {
      Lexer lexer = new Lexer(new StringReader(input), "unknown");
      Symbol token;
      StringBuilder builder = new StringBuilder();
      List<String> list = List.empty();
      do {
         token = lexer.next_token();
         builder.append(Terminals.dumpTerminal(token)).append('\n');
         list = list.append(Terminals.dumpTerminal(token));
      } while (token.sym != Terminals.EOF);
      return builder.toString();
      //return list;
   }

   private void trun(String input, String... output) throws IOException {
      StringBuilder builder = new StringBuilder();
      for (String x : output)
         builder.append(x).append('\n');
      softly.assertThat(run(input))
         .as("%s", input)
         .isEqualTo(builder.toString());
   }

   private void erun(String input, String message) throws IOException {
      softly.assertThatThrownBy(() -> run(input))
         .as("%s", input)
         .isInstanceOf(CompilerError.class)
         .hasMessage(message);
   }

   @Rule
   public final JUnitSoftAssertions softly = new JUnitSoftAssertions();

   @Test
   public void lexerTest1() throws IOException {
      // whitespaces
      trun("    \t\n\n\n\t\r\n\r\n  ", "6:3-6:3 EOF");

      // comentario
      trun("$ uma linha de comentario\n", "2:1-2:1 EOF");
      trun("$uma linha de comentario\n", "2:1-2:1 EOF");
      trun("$ uma linha de comentario", "1:26-1:26 EOF");
      trun("/$ a block comment $/", "1:22-1:22 EOF");
      trun("/$ a\nmultiline\ncomment $/", "3:11-3:11 EOF");
      trun("/$ begin $$$ end $/", "1:20-1:20 EOF");
      trun("/$ begin $$$$ end $/", "1:21-1:21 EOF");
      trun("/$ begin $$$$/", "1:15-1:15 EOF");
      trun("/$ outer /$ inner $/ outer $/", "1:30-1:30 EOF");
      erun("/$ a /$ ab /$ abc $/ ba", "1:24-1:24 lexical error: unclosed comment");

      // pontuacao
      trun("==", "1:1-1:3 ASSIGN", "1:3-1:3 EOF");
      trun("=", "1:1-1:2 EQ", "1:2-1:2 EOF");
      trun("(", "1:1-1:2 LPAREN", "1:2-1:2 EOF");
      trun(")", "1:1-1:2 RPAREN", "1:2-1:2 EOF");
      trun(",", "1:1-1:2 COMMA", "1:2-1:2 EOF");

      // operadores
      trun("+", "1:1-1:2 PLUS", "1:2-1:2 EOF");
      trun("-", "1:1-1:2 MINUS", "1:2-1:2 EOF");
      trun("*", "1:1-1:2 TIMES", "1:2-1:2 EOF");
      trun("/", "1:1-1:2 DIV", "1:2-1:2 EOF");
      trun("%", "1:1-1:2 MOD", "1:2-1:2 EOF");
      trun("!=", "1:1-1:3 NE", "1:3-1:3 EOF");
      trun("<", "1:1-1:2 LT", "1:2-1:2 EOF");
      trun("<=", "1:1-1:3 LE", "1:3-1:3 EOF");
      trun(">", "1:1-1:2 GT", "1:2-1:2 EOF");
      trun("&", "1:1-1:2 AND", "1:2-1:2 EOF");
      trun("|", "1:1-1:2 OR", "1:2-1:2 EOF");

      // booleano
      trun("true", "1:1-1:5 FBOOL(true)", "1:5-1:5 EOF");
      trun("false", "1:1-1:6 FBOOL(false)", "1:6-1:6 EOF");

      // integer literals
      trun("26342", "1:1-1:6 FINT(26342)", "1:6-1:6 EOF");
      trun("0", "1:1-1:2 FINT(0)", "1:2-1:2 EOF");
      //trun("+75"    , "1:1-1:4 FINT(75)"    , "1:4-1:4 EOF");
      //trun("-75"    , "1:1-1:4 FINT(-75)"   , "1:4-1:4 EOF");

      // string literals
      trun("\"A\"", "1:1-1:4 FSTRING(A)", "1:4-1:4 EOF");
      trun("\"b\"", "1:1-1:4 FSTRING(b)", "1:4-1:4 EOF");
      trun("\"*\"", "1:1-1:4 FSTRING(*)", "1:4-1:4 EOF");
      trun("\" \"", "1:1-1:4 FSTRING( )", "1:4-1:4 EOF");
      trun("\"\t\"", "1:1-1:4 FSTRING(\t)", "1:4-1:4 EOF");
      trun("\"\\b\"", "1:1-1:5 FSTRING(\b)", "1:5-1:5 EOF");
      trun("\"\\t\"", "1:1-1:5 FSTRING(\t)", "1:5-1:5 EOF");
      trun("\"\\n\"", "1:1-1:5 FSTRING(\n)", "1:5-1:5 EOF");
      trun("\"\\r\"", "1:1-1:5 FSTRING(\r)", "1:5-1:5 EOF");
      trun("\"\\f\"", "1:1-1:5 FSTRING(\f)", "1:5-1:5 EOF");
      trun("\"\\\"\"", "1:1-1:5 FSTRING(\")", "1:5-1:5 EOF");
      trun("\"\\065\"", "1:1-1:7 FSTRING(A)", "1:7-1:7 EOF");
      erun("\"\\x\"", "1:2-1:4 lexical error: invalid escape sequence in string literal");
      trun("\"ABC\"", "1:1-1:6 FSTRING(ABC)", "1:6-1:6 EOF");
      erun("\"\n\"", "1:2-1:3 lexical error: invalid newline in string literal");

      // keywords
      trun("bool", "1:1-1:5 BOOL", "1:5-1:5 EOF");
      trun("int", "1:1-1:4 INT", "1:4-1:4 EOF");
      trun("string", "1:1-1:7 STRING", "1:7-1:7 EOF");
      trun("if", "1:1-1:3 IF", "1:3-1:3 EOF");
      trun("then", "1:1-1:5 THEN", "1:5-1:5 EOF");
      trun("else", "1:1-1:5 ELSE", "1:5-1:5 EOF");
      trun("while", "1:1-1:6 WHILE", "1:6-1:6 EOF");
      trun("do", "1:1-1:3 DO", "1:3-1:3 EOF");
      trun("let", "1:1-1:4 LET", "1:4-1:4 EOF");
      trun("in", "1:1-1:3 IN", "1:3-1:3 EOF");

      // identifiers
      trun("nome", "1:1-1:5 ID(nome)", "1:5-1:5 EOF");
      trun("camelCase", "1:1-1:10 ID(camelCase)", "1:10-1:10 EOF");
      trun("with_underscore", "1:1-1:16 ID(with_underscore)", "1:16-1:16 EOF");
      trun("A1b2C33", "1:1-1:8 ID(A1b2C33)", "1:8-1:8 EOF");
      trun("set+", "1:1-1:4 ID(set)", "1:4-1:5 PLUS", "1:5-1:5 EOF");
      trun("45let", "1:1-1:3 FINT(45)", "1:3-1:6 LET", "1:6-1:6 EOF");
      erun("_invalid", "1:1-1:2 lexical error: invalid character '_'");
   }
}
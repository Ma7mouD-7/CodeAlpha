package com.ma7moud27.shelfy.local

import com.ma7moud27.shelfy.model.author.Author
import com.ma7moud27.shelfy.utils.enums.Category

object LocalDataClient : LocalDataSource {
    override fun getCategoryList(numOfItems: Int): List<Category> = Category.values().take(numOfItems)

    override fun getTopAuthors(): List<Author> {
        return listOf(
            Author(name = "Leo Tolstoy", key = "OL26783A"),
            Author(name = "Stephen King", key = "OL2162284A"),
            Author(name = "J. K. Rowling", key = "OL23919A"),
            Author(name = "Dan Brown", key = "OL39307A"),
            Author(name = "William Shakespeare", key = "OL9388A"),
            Author(name = "Agatha Christie", key = "OL27695A"),
            Author(name = "John Ronald Reuel Tolkien", key = "OL26320A"),
            Author(name = "Danielle Steel", key = "OL24452A"),
            Author(name = "James Patterson", key = "OL22258A"),
            Author(name = "John Grisham", key = "OL39329A"),
            Author(name = "Nicholas Sparks", key = "OL19597A"),
            Author(name = "R. L. Stine", key = "OL35524A"),
            Author(name = "Sidney Sheldon", key = "OL225246A"),
            Author(name = "Dr. Seuss", key = "OL2622837A"),
            Author(name = "Barbara Cartland", key = "OL22022A"),
            Author(name = "Roald Dahl", key = "OL34184A"),
            Author(name = "Stephenie Meyer", key = "OL1391085A"),
            Author(name = "Georges Simenon", key = "OL2660391A"),
            Author(name = "C. S. Lewis", key = "OL31574A"),
            Author(name = "Lewis Carroll", key = "OL22098A"),
            Author(name = "Michael Crichton", key = "OL28257A"),
            Author(name = "Tom Clancy", key = "OL25277A"),
            Author(name = "Robert Ludlum", key = "OL388652A"),
            Author(name = "Corín Tellado", key = "OL3204120A"),
            Author(name = "Horatio Alger", key = "OL316972A"),
            Author(name = "Dean Koontz", key = "OL33088A"),
            Author(name = "Nora Roberts", key = "OL18977A"),
            Author(name = "Beatrix Potter", key = "OL32541A"),
            Author(name = "Enid Blyton", key = "OL233814A"),
            Author(name = "Charles Dickens", key = "OL24638A"),
        )
    }
}

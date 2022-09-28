package test.core;

import com.github.scipioutils.core.data.structure.TreeStructure;
import org.junit.jupiter.api.Test;
import test.core.bean.Menu;

import java.util.List;

/**
 * @since 2022/9/21
 */
public class TreeTest {

    @Test
    public void testBuild0() {
        List<Menu> origList = Menu.createTestData0();

//        Menu root = TreeStructure.buildTree(new Menu("", "ROOT", null), origList);
//        System.out.println(root);

        List<Menu> forest = TreeStructure.buildForest("", origList);
        System.out.println(forest);
    }

}

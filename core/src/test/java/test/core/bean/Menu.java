package test.core.bean;

import com.github.scipioutils.core.data.structure.TreeNode;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * @since 2022/9/21
 */
@Data
@NoArgsConstructor
public class Menu implements TreeNode<Menu> {

    private String id;

    private String name;

    private String parentId;

    private List<Menu> children = new ArrayList<>();

    public Menu(String id, String name, String parentId) {
        this.id = id;
        this.name = name;
        this.parentId = parentId;
    }

}

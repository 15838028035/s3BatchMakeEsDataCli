package cn.thinkit.config;


/**
 * 配置文件配置项
 */
public class ConfigProp {
    private String esNode;

    private String esIndex;

    private String esType;

    private String esClusterName;

    public String getEsNode() {
        return esNode;
    }

    public void setEsNode(String esNode) {
        this.esNode = esNode;
    }

    public String getEsIndex() {
        return esIndex;
    }

    public void setEsIndex(String esIndex) {
        this.esIndex = esIndex;
    }

    public String getEsType() {
        return esType;
    }

    public void setEsType(String esType) {
        this.esType = esType;
    }

    public String getEsClusterName() {
        return esClusterName;
    }

    public void setEsClusterName(String esClusterName) {
        this.esClusterName = esClusterName;
    }
}

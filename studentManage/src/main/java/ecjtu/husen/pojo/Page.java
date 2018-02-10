package ecjtu.husen.pojo;

import java.util.ArrayList;
import java.util.List;


/**
 * @author 11785
 */
public class Page<T> {
    /**
     * 总记录
     */
    private Long total;

    /**
     * 每页大小
     */
    private Integer pageSize;

    /**
     * 当前页
     */
    private Integer currentPage;

    /**
     * 总页数
     */
    private Integer totalPage;

    /**
     * 当前页的记录
     */
    private List<T> page = new ArrayList<T>();

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(Integer currentPage) {
        this.currentPage = currentPage;
    }

    public Integer getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(Integer totalPage) {
        this.totalPage = totalPage;
    }

    public List<T> getPage() {
        return page;
    }

    public void setPage(List<T> page) {
        this.page = page;
    }
}

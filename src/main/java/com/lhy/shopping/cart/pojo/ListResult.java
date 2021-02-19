package com.lhy.shopping.cart.pojo;

import com.github.pagehelper.Page;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ListResult<T> {

    private int totalRecords;
    private int currentPage;
    private List<T> list;
    private int maxResult;
    private int totalPages;

    private int maxNavigationPage = 4;

    private List<Integer> navigationPages;


    public ListResult(List<T> list, Page page) {
        // Total Records
        this.currentPage = page.getPageNum();
        this.list = list;
        this.maxResult = page.getPageSize();
        this.totalPages = page.getPages();

        if (maxNavigationPage > totalPages) {
            this.maxNavigationPage = totalPages;
        }

        this.calcNavigationPages();
    }

    private void calcNavigationPages() {

        this.navigationPages = new ArrayList<>();

        int current = Math.min(this.currentPage, this.totalPages);

        int begin = current - this.maxNavigationPage / 2;
        int end = current + this.maxNavigationPage / 2;

        navigationPages.add(1);
        if (begin > 2) {
            navigationPages.add(-1);
        }

        for (int i = begin; i <= end; i++) {
            if (i > 1 && i < this.totalPages) {
                navigationPages.add(i);
            }
        }

        if (end < this.totalPages - 1) {
            navigationPages.add(-1);
        }
        navigationPages.add(this.totalPages);
    }
}

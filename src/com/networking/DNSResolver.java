package com.networking;

import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.concurrent.ConcurrentHashMap;

public class DNSResolver {
    private final Map<String, String> dnsMap;

    public DNSResolver() {
        this.dnsMap = new ConcurrentHashMap<>();
        initializeDefaultRecords();
    }

    private void initializeDefaultRecords() {
        addRecord("www.google.com", "8.8.8.8");
        addRecord("www.example.com", "93.184.216.34");
        addRecord("www.one.com", "1.1.1.1");
    }

    public void addRecord(String domain, String ipAddress) {
        if (isInvalid(domain) || isInvalid(ipAddress)) {
            throw new IllegalArgumentException("Invalid domain or IP address");
        }
        dnsMap.put(domain.toLowerCase(), ipAddress);
    }

    public void removeRecord(String domain) {
        if (isInvalid(domain)) {
            throw new IllegalArgumentException("Invalid domain");
        }
        dnsMap.remove(domain.toLowerCase());
    }

    public String iterativeResolve(String domain) {
        if (isInvalid(domain)) {
            throw new IllegalArgumentException("Invalid domain");
        }
        return dnsMap.getOrDefault(domain.toLowerCase(), "Not found");
    }

    public String recursiveResolve(String domain) {
        if (isInvalid(domain)) {
            throw new IllegalArgumentException("Invalid domain");
        }
        return recursiveHelper(domain.toLowerCase(), new ArrayList<>(dnsMap.keySet()), 0);
    }

    private String recursiveHelper(String domain, List<String> keys, int index) {
        if (index >= keys.size()) return "Not found";
        if (keys.get(index).equals(domain)) return dnsMap.get(domain);
        return recursiveHelper(domain, keys, index + 1);
    }

    public String bfsResolve(String domain) {
        if (isInvalid(domain)) {
            throw new IllegalArgumentException("Invalid domain");
        }
        domain = domain.toLowerCase();
        Queue<String> queue = new LinkedList<>();
        queue.add(domain);
        while (!queue.isEmpty()) {
            String currentDomain = queue.poll();
            if (dnsMap.containsKey(currentDomain)) {
                return dnsMap.get(currentDomain);
            }
        }
        return "Not found";
    }

    private boolean isInvalid(String input) {
        return input == null || input.isBlank();
    }

    @Override
    public String toString() {
        return dnsMap.entrySet()
                .stream()
                .map(entry -> entry.getKey() + ": " + entry.getValue())
                .reduce((a, b) -> a + "\n" + b)
                .orElse("No records found.");
    }

    public static void main(String[] args) {
        DNSResolver resolver = new DNSResolver();

        System.out.println("Iterative Resolve:");
        System.out.println("www.google.com: " + resolver.iterativeResolve("www.google.com"));
        System.out.println("www.example.com: " + resolver.iterativeResolve("www.example.com"));

        System.out.println("\nRecursive Resolve:");
        System.out.println("www.google.com: " + resolver.recursiveResolve("www.google.com"));
        System.out.println("www.example.com: " + resolver.recursiveResolve("www.example.com"));

        System.out.println("\nBFS Resolve:");
        System.out.println("www.google.com: " + resolver.bfsResolve("www.google.com"));
        System.out.println("www.one.com: " + resolver.bfsResolve("www.one.com"));

        resolver.addRecord("www.newsite.com", "192.168.1.1");
        System.out.println("\nNewly added record:");
        System.out.println("www.newsite.com: " + resolver.iterativeResolve("www.newsite.com"));

        resolver.removeRecord("www.one.com");
        System.out.println("\nAfter removing www.one.com:");
        System.out.println(resolver);
    }
}

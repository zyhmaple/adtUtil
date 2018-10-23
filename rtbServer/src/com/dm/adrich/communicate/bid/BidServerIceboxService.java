package com.dm.adrich.communicate.bid;

import Ice.Communicator;
import Ice.ObjectAdapter;
import IceBox.Service;

public class BidServerIceboxService
  implements Service
{
  private ObjectAdapter adapter;

  public void start(String name, Communicator communicator, String[] args)
  {
    this.adapter = communicator.createObjectAdapter(
      "adrichBid-" + name);

    String adrichBidIdentity = communicator.getProperties()
      .getProperty("bidAdapter.Identity");

    this.adapter.add(new GeneralParseRequestServiceI(), 
      communicator.stringToIdentity(adrichBidIdentity));

    this.adapter.activate();
  }

  public void stop()
  {
    this.adapter.destroy();
  }
}